package com.seamless.customer.bi.aggregator.aggregate

import com.seamless.customer.bi.aggregator.aggregate.AbstractAggregator
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled

@Slf4j
class ResellerRegionAccess extends AbstractAggregator {

    static final def TABLE = "reseller_region_access_info"

    @Autowired
    @Qualifier("refill")
    protected JdbcTemplate refillJdbcTemplate

    @Value('${ResellerRegion.limit:10}')
    int limit


    @Autowired
    protected JdbcTemplate jdbcTemplate


    @Scheduled(cron = '${ResellerRegionAccess.cron:*/3 * * * * ?}')
    void aggregate() {
        log.info("ResellerRegion Aggregator started***************************************************************************" + new Date());
        addResellerRegionData();
        log.info("ResellerRegion Aggregator ended*****************************************************************************");
    }

    private void addResellerRegionData() {
        def sqlQuery = "SELECT tab.regionAccess, tab.resellerId from ( select rep1.parameter_value as regionAccess, cr.tag as resellerId from reseller_extra_params rep left join reseller_extra_params rep1 on rep1.receiver_key = rep.receiver_key and rep1.parameter_key like 'accessibleRegionNames' inner join commission_receivers cr on cr.receiver_key = rep.receiver_key where rep.parameter_key like 'isSyncFromHrPortal' and rep.parameter_value like 'true' UNION select rep.parameter_value as regionAccess, cr.tag as resellerId from reseller_extra_params rep inner join commission_receivers cr on cr.receiver_key = rep.receiver_key where rep.parameter_key like 'regionName') as tab ORDER by tab.resellerId limit ? offset ?"
        def offset = 0
        while (true) {
            def resellerRefillResult = refillJdbcTemplate.queryForList(sqlQuery, limit, offset);
            if (resellerRefillResult) {
                List<ResellerRegionResponse> list = new ArrayList<>();
                resellerRefillResult.forEach({ row ->
                    ResellerRegionResponse regionResponse = new ResellerRegionResponse();
                    regionResponse.setRegionAccess(row.get("regionAccess"));
                    regionResponse.setResellerId(row.get("resellerId"));
                    list.add(regionResponse)
                })
                insertData(list);
            } else {
                break;
            }

            offset += limit;
        }
    }


    private void insertData(List resellerRegionResponse) {
        log.info("Updating reseller_region_access_info table in bi db...");
        if (resellerRegionResponse.size() != 0) {
            def sql = "INSERT INTO ${TABLE} (reseller_id, region_access) VALUES (?,?) ON DUPLICATE KEY UPDATE region_access = VALUES(region_access)";
            def batchUpdate = jdbcTemplate.batchUpdate(sql, [
                    setValues   : { ps, i ->
                        def row = resellerRegionResponse[i]
                        ps.setString(1, row.resellerId)
                        ps.setString(2, row.regionAccess)
                    },
                    getBatchSize: { resellerRegionResponse.size() }
            ] as BatchPreparedStatementSetter)
        }
    }
}

class ResellerRegionResponse {
    private String resellerId
    private String regionAccess

    ResellerRegionResponse(String resellerId, String regionAccess) {
        this.resellerId = resellerId
        this.regionAccess = regionAccess
    }

    ResellerRegionResponse() {
    }


    String getResellerId() {
        return resellerId
    }

    void setResellerId(String resellerId) {
        this.resellerId = resellerId
    }

    String getRegionAccess() {
        return regionAccess
    }

    void setRegionAccess(String regionAccess) {
        this.regionAccess = regionAccess
    }

    @Override
    public String toString() {
        return "ResellerRegionResponse{" +
                "resellerId='" + resellerId + '\'' +
                ", regionAccess='" + regionAccess + '\'' +
                '}';
    }
}
