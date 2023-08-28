oc delete -f cronjob.yaml
oc delete -f cronjob-2.yaml 
oc delete -f cronjob-3.yaml 
oc delete cm scriptfiles -n ers-prod 
oc delete secret db-user  -n ers-prod
oc delete secret db-pass  -n ers-prod
