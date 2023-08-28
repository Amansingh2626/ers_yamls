oc -n ers-prod delete -f  01-AlertManagerConfigmap.yaml
oc -n ers-prod delete -f  02-AlertTemplateConfigMap.yaml
oc -n ers-prod delete -f  03-Service.yaml
oc -n ers-prod delete -f  04-Deployment.yaml
oc -n ers-prod delete -f  05-alertmanager-route.yaml
