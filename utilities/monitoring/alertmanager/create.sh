oc -n ers-prod apply -f  01-AlertManagerConfigmap.yaml
oc -n ers-prod apply -f  02-AlertTemplateConfigMap.yaml
oc -n ers-prod apply -f  03-Service.yaml
oc -n ers-prod apply -f  04-Deployment.yaml
oc -n ers-prod apply -f  05-alertmanager-route.yaml
