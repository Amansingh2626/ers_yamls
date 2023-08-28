oc -n ers-prod delete configmap logstash-config-nginx  
oc -n ers-prod delete configmap logstash-pipeline-nginx 
#oc -n ers-prod delete configmap logstash-nginx-log4j 
oc -n ers-prod delete secret  elastic-search-secret-nginx 
