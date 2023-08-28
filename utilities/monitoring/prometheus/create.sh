oc -n ers-prod create configmap prometheus-ers-prod-rules --from-file=rules
oc -n ers-prod apply -f  01-ers-prometheus-configmap.yaml
oc -n ers-prod apply -f  01-ers-prometheus-bearer-token.yaml 
oc -n ers-prod apply -f  02-ers-prometheus-deployment.yaml
oc -n ers-prod apply -f  03-ers-prometheus-svc.yaml
oc -n ers-prod apply -f  04-ers-rod-ext-prometheus-route.yaml 
