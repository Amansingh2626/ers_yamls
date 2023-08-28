oc  -n ers-prod create configmap logstash-config-nginx --from-file=config
oc  -n ers-prod create configmap logstash-pipeline-nginx --from-file=pipeline
#oc  -n ers-prod create configmap logstash-nginx-log4j --from-file=log4j2.properties
oc  -n ers-prod create secret generic elastic-search-secret-nginx --from-file=ca.crt
