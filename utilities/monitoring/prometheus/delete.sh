oc -n ers-prod delete configmap prometheus-ers-prod-rules 
oc -n ers-prod delete -f  01-ers-prometheus-configmap.yaml
oc -n ers-prod delete -f  01-ers-prometheus-bearer-token.yaml 
oc -n ers-prod delete -f  02-ers-prometheus-deployment.yaml
oc -n ers-prod delete -f  03-ers-prometheus-svc.yaml
oc -n ers-prod delete -f  04-ers-rod-ext-prometheus-route.yaml 
