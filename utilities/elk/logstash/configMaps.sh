
oc -n ers-prod create configmap logstash-config-txlog --from-file=config_txlog
oc -n ers-prod create configmap logstash-config-others --from-file=config_others

oc  -n ers-prod create configmap logstash-pipeline-txlog --from-file=pipeline_txlog
oc  -n ers-prod create configmap logstash-pipeline-others --from-file=pipeline_others

oc  -n ers-prod create secret generic elastic-search-secret --from-file=ca.crt
#oc  -n ers-prod create configmap logstash-log4j --from-file=log4j2.properties
