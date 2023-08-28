import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import groovy.util.logging.Log4j
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

@Log4j
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
        def sqlQuery = "SELECT system,reference_number FROM transaction_reconciliations where reconciliation_status='RECON_NEEDED'"
        Map<String, String> openErsReference = new HashMap<String, String>()
        boolean flag=false;
        try {
            openErsReference = jdbcTemplate.query(sqlQuery,
                    new ResultSetExtractor() {
                        @Override
                        Map<String, String> extractData(ResultSet rs) {
                            Map<String, String> map = new HashMap<String, String>()
                            while (rs.next()) {
                                flag=true;
                                map.put(rs.getString("reference_number"), rs.getString("system"))
                            }
                            return map
                        }
                    })
        } catch (Exception e) {
        }
        List<DailyErsreferenceUdpateModel> DailyErsreferenceUdpateList = new ArrayList<>();
        if (flag){
            for (entry in openErsReference) {
                SlcResponse slcObject = fetchStatus(entry.key, entry.value)
                def serverResponse = "RECON_NOT_NEEDED";
                AdditionalFields aa= slcObject.additionalFields;

                log.info(aa.serverResponse.toString())
                log.info(aa.serverResponse)
                if (aa.serverResponse.toString() == 'fail') {
                    ReversalResponse reversalResponse = fetchReverseTransaction(entry.key);
                    if (reversalResponse.getState().equals("Completed")) {
                        serverResponse = "RECON_DONE"
                    } else {
                        serverResponse = "RECON_FAILED"
                    }
                }
                DailyErsreferenceUdpateModel dailyErsreferenceUdpateModel = new DailyErsreferenceUdpateModel(entry.key, serverResponse)
                DailyErsreferenceUdpateList.add(dailyErsreferenceUdpateModel)
            }
            updateData(DailyErsreferenceUdpateList)
        }

    }
    private void updateData(List DailyErsreferenceUdpateList) {
        log.info("Updating DailyErsreferenceUdpate table in db...");
        if (DailyErsreferenceUdpateList != null) {
            def sql = "INSERT INTO ${TABLE} (reference_number,reconciliation_status) VALUES (?,?) ON DUPLICATE KEY UPDATE reconciliation_status=VALUES(reconciliation_status)";
            log.debug(sql);
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = DailyErsreferenceUdpateList[i]
                        def index = 0
                        ps.setString(++index, row.ersReference)
                        ps.setString(++index, row.serverResponse)

                    },
                    getBatchSize: { 1 }
            ] as BatchPreparedStatementSetter)
            log.info("Updated 1 row in transaction_reconciliations table");
        }
    }
    private SlcResponse fetchStatus(String ersReference, String serverType) {
    log.info(" sending query request for reference "+ersReference);
        log.info(" request URL "+slcTransactionUrl);
        log.info(" request for server  "+serverType)
        RESTClient client = new RESTClient(slcTransactionUrl)
        try {
            def response = client.post(
                    accept: ContentType.JSON
            ){
                json operationId:serverType+"QUERY", customerId:"GP",additionalFields:[ersReference:ersReference,serverType:serverType]
            } ;

            ObjectMapper objectMapper = new ObjectMapper();
            log.info("response fetched from SLC!")
            log.debug(response.contentAsString)
            return objectMapper.readValue(response.contentAsString, SlcResponse.class)
        }
        catch (Exception err) {
            log.error(err)
        }
    }
    private ReversalResponse fetchReverseTransaction(String ersReference) {
        RESTClient client = new RESTClient(txeTransactionUrl)
        try {

            def response = client.post(
                    accept: ContentType.JSON,
                    headers: [  clientId: clientId, apiKey: apiKey,userId :userId,validateOnly:"false" ]
            ){
                json reasonCode:"reversal",ersReference:ersReference
            } ;

            ObjectMapper mapper = new ObjectMapper()
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            return mapper.readValue(response.contentAsString,ReversalResponse.class)
        }
        catch (RESTClientException e) {
            log.error(e)
        }
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

class SlcResponse
{
    private int resultCode;
    private String resultDescription;
    private AdditionalFields additionalFields;

    SlcResponse() {
    }

    SlcResponse(int resultCode, String resultDescription, AdditionalFields additionalFields) {
        this.resultCode = resultCode
        this.resultDescription = resultDescription
        this.additionalFields = additionalFields
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

    AdditionalFields getAdditionalFields() {
        return additionalFields
    }

    void setAdditionalFields(AdditionalFields additionalFields) {
        this.additionalFields = additionalFields
    }
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
