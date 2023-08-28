import com.seamless.common.StringUtils
import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional
import wslite.rest.ContentType
import wslite.rest.RESTClient
import wslite.rest.RESTClientException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.ResultSetExtractor
import java.sql.ResultSet
import org.apache.http.entity.*
import  java.io.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@Slf4j
@Transactional
public class DailyErsreferenceUdpate extends AbstractAggregator {
    static final def TABLE = "transaction_reconciliations"

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Value('${DailyErsreferenceUdpate.slcTransactionUrl:}')
    String slcTransactionUrl;

    @Value('${DailyErsreferenceUdpate.txeTransactionUrl:}')
    String txeTransactionUrl;

    @Value('${DailyErsreferenceUdpate.clientId:}')
    String clientId;

    @Value('${DailyErsreferenceUdpate.apiKey:}')
    String apiKey;

    @Value('${DailyErsreferenceUdpate.userId:}')
    String userId;

    @Value('${DailyErsreferenceUdpate.validateOnly:}')
    boolean validateOnly;

    @Transactional
    @Scheduled(cron = '${DailyErsreferenceUdpate.cron:*/3 * * * * ?}')
    void aggregate() {
        log.info("DailyErsreferenceUdpate Aggregator started***************************************************************************" + new Date());
        fetchData();
        log.info("DailyErsreferenceUdpate Aggregator ended*****************************************************************************");
    }

    private void fetchData() {
        def sqlQuery = "SELECT system,reference_number,transaction_id,transaction_time,receiver_msisdn,transaction_amount FROM transaction_reconciliations where reconciliation_status='RECON_NEEDED'"
        Map<String, SqlQueryInput> openErsReference = new HashMap<String, String>()
        boolean flag=false;
        try {
            openErsReference = jdbcTemplate.query(sqlQuery,
                    new ResultSetExtractor() {
                        @Override
                        Map<String, SqlQueryInput> extractData(ResultSet rs) {
                            List<SqlQueryInput> sqlQueryInputList = new ArrayList<>();
                            Map<String, String> map = new HashMap<String, String>()
                            while (rs.next()) {
                                flag=true;
                                SqlQueryInput sqlQueryInput = new SqlQueryInput();
                                sqlQueryInput.setReference_number(rs.getString("reference_number"));
                                sqlQueryInput.setSystem(rs.getString("system"));
                                sqlQueryInput.setTransaction_id(rs.getString("transaction_id"));
                                String interTransactionDate = rs.getString("transaction_time");
                                sqlQueryInput.setTransaction_date(interTransactionDate == null ? "" : interTransactionDate.split(" ")[0]);
                                sqlQueryInput.setMsisdn(rs.getString("receiver_msisdn"));
                                sqlQueryInput.setTransaction_amount(rs.getDouble("transaction_amount"));
                                map.put(sqlQueryInput.getReference_number(), sqlQueryInput);
                            }
                            return map
                        }
                    });
        }catch(Exception e){
            log.error("Exception :"+ExceptionUtils.getStackTrace(e))
        }
        List<DailyErsreferenceUdpateModel> dailyErsreferenceUdpateList = new ArrayList<>();
        if (flag){
            openErsReference.forEach({ key, value ->
                SlcResponse slcObject;
                if(value.getSystem().equalsIgnoreCase("SKITTO")) {
                    slcObject = fetchStatus(value.getTransaction_id(), value.getSystem(), null, null, null)
                } else {
                    slcObject = fetchStatus(value.getReference_number(), value.getSystem(), value.getTransaction_date(), value.getMsisdn(), value.getTransaction_amount().toString())
                }
                def serverResponse = "RECON_NOT_NEEDED";
                if(slcObject==null){
                    serverResponse = "RECON_FAILED"
                }
                else if (slcObject.getResultCode() != 0) {
                    ReversalResponse reversalResponse = fetchReverseTransaction(key);
                    if(java.util.Objects.isNull(reversalResponse)||!reversalResponse.getResultCode().equals("0")){
                        serverResponse = "RECON_FAILED"
                    }
                    else {
                        serverResponse = "RECON_DONE"
                    }
                }
                DailyErsreferenceUdpateModel dailyErsreferenceUdpateModel = new DailyErsreferenceUdpateModel(key, serverResponse)
                dailyErsreferenceUdpateList.add(dailyErsreferenceUdpateModel)
            })
            updateData(dailyErsreferenceUdpateList)
        }
    }
    private void updateData(List dailyErsreferenceUdpateList) {
        log.info("Updating DailyErsreferenceUdpate table in db...");
        if (dailyErsreferenceUdpateList != null) {
            def sql = "INSERT INTO ${TABLE} (reference_number,reconciliation_status) VALUES (?,?) ON DUPLICATE KEY UPDATE reconciliation_status=VALUES(reconciliation_status)";
            log.debug(sql);
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = dailyErsreferenceUdpateList[i]
                        def index = 0
                        ps.setString(++index, row.ersReference)
                        ps.setString(++index, row.serverResponse)

                    },
                    getBatchSize: { 1 }
            ] as BatchPreparedStatementSetter)
            log.info("Updated 1 row in transaction_reconciliations table");
        }
    }
    private SlcResponse fetchStatus(String ersReference, String serverType, String transactionDate, String msisdn, String transactionAmount) {
        RESTClient client = new RESTClient(slcTransactionUrl)
        if(com.seamless.common.StringUtils.isBlank(ersReference) || StringUtils.isBlank(serverType)){
            log.warn("ersReference or serverType is blank, Failed to recouncil.");
            log.warn(ersReference +" ersReference is blank or empty");
            log.warn(serverType +" serverType is blank or empty");
            return null;
        }
        log.info("Ers Reference: " + ersReference);
        log.info("Transaction Date: " + transactionDate);
        log.info("Server Type: " + serverType);
        log.info("MSISDN: " + msisdn);
        log.info("Transaction Amount: " + transactionAmount);
        try {
            def response = client.post(
                    accept: ContentType.JSON
            ){
                if(serverType.equalsIgnoreCase("SKITTO")) {
                    json operationId:serverType+"QUERY", customerId:"GP",additionalFields:[ersReference:ersReference,serverType:serverType]
                } else {
                    json additionalFields:[ersReference:ersReference,serverType:serverType,paymentDate:transactionDate,msisdn:msisdn,amount:transactionAmount]
                }
            } ;

            ObjectMapper objectMapper = new ObjectMapper();
            log.info("response fetched from SLC!")
            return objectMapper.readValue(response.contentAsString, SlcResponse.class)
        }
        catch (Exception err) {
            log.error("Error in QueryTopup :"+err)
        }
        return null;
    }
    private ReversalResponse fetchReverseTransaction(String ersReference) {
        RESTClient client = new RESTClient(txeTransactionUrl)
        try {

            def response = client.post(
                    accept: ContentType.JSON,
                    headers: [  "ref-prefix":"BD",clientId: clientId, apiKey: apiKey,userId :userId,validateOnly:"false" ]
            ){
                json reasonCode:"reversal",ersReference:ersReference
            } ;
            log.info("Succes in ReversalResponse");
            ObjectMapper mapper = new ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            return mapper.readValue(response.contentAsString,ReversalResponse.class)
        }
        catch (RESTClientException e) {
            log.error("Failed in ReversalResponse :"+ ExceptionUtils.getStackTrace(e))
        }
        return null;
    }
}
class SqlQueryInput{
    private String system;
    private String reference_number;
    private String transaction_id;
    private String transaction_date;
    private String msisdn;
    private Double transaction_amount;

    SqlQueryInput(String system, String reference_number, String transaction_id, Double transaction_date, String msisdn, String transaction_amount) {
        this.system = system
        this.reference_number = reference_number
        this.transaction_id = transaction_id
        this.transaction_date = transaction_date
        this.msisdn = msisdn
        this.transaction_amount = transaction_amount
    }

    SqlQueryInput(){

    }

    String getSystem() {
        return system
    }

    void setSystem(String system) {
        this.system = system
    }

    Double getTransaction_amount() {
        return transaction_amount
    }

    void setTransaction_amount(Double transaction_amount) {
        this.transaction_amount = transaction_amount
    }

    String getReference_number() {
        return reference_number
    }

    void setReference_number(String reference_number) {
        this.reference_number = reference_number
    }

    String getTransaction_id() {
        return transaction_id
    }

    void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id
    }

    String getTransaction_date() {
        return transaction_date
    }

    void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date
    }

    String getMsisdn() {
        return msisdn
    }

    void setMsisdn(String msisdn) {
        this.msisdn = msisdn
    }



}

class ReversalResponse{
    private String resultCode;
    private String resultMessage;
    private String state;

    ReversalResponse(String resultCode, String resultMessage, String state) {
        this.resultCode = resultCode
        this.resultMessage = resultMessage
        this.state = state
    }

    ReversalResponse() {

    }


    String getResultCode() {
        return resultCode
    }

    void setResultCode(String resultCode) {
        this.resultCode = resultCode
    }

    String getResultMessage() {
        return resultMessage
    }

    void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }
}
class DailyErsreferenceUdpateModel {
    private String ersReference;
    private String serverResponse;

    DailyErsreferenceUdpateModel(String ersReference, String serverResponse) {
        this.ersReference = ersReference
        this.serverResponse = serverResponse
    }

    String getErsReference() {
        return ersReference
    }

    void setErsReference(String ersReference) {
        this.ersReference = ersReference
    }

    String getServerResponse() {
        return serverResponse
    }

    void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SlcResponse
{
    private int resultCode;
    private String resultDescription;
//    private AdditionalFields additionalFields;

    SlcResponse() {
    }

    SlcResponse(int resultCode, String resultDescription) {
        this.resultCode = resultCode
        this.resultDescription = resultDescription
//        this.additionalFields = additionalFields
    }

    int getResultCode() {
        return resultCode
    }

    void setResultCode(int resultCode) {
        this.resultCode = resultCode
    }

    String getResultDescription() {
        return resultDescription
    }

    void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription
    }

//    AdditionalFields getAdditionalFields() {
//        return additionalFields
//    }
//
//    void setAdditionalFields(AdditionalFields additionalFields) {
//        this.additionalFields = additionalFields
//    }
}


class AdditionalFields
{
    private String serverResponse;

    AdditionalFields() {
    }

    AdditionalFields(String serverResponse) {
        this.serverResponse = serverResponse
    }
    String getServerResponse() {
        return serverResponse
    }

    void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse
    }
}
