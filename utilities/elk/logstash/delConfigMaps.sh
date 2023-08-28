

oc -n ers-prod delete configmap logstash-config-txlog
oc -n ers-prod delete configmap logstash-config-others

oc  -n ers-prod delete configmap logstash-pipeline-txlog
oc  -n ers-prod delete configmap logstash-pipeline-others

oc  -n ers-prod delete secret elastic-search-secret 
#oc  -n ers-prod delete configmap logstash-log4j 
