oc create secret generic db-pass --from-literal=dbpass=refill -n ers-prod
oc create secret generic db-user --from-literal=dbuser=refill -n ers-prod
oc create configmap scriptfiles --from-file=files -n ers-prod 
oc apply -f cronjob.yaml
oc apply -f cronjob-2.yaml 
oc apply -f cronjob-3.yaml 
