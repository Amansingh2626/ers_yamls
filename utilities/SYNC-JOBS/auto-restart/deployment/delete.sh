oc delete -f cronjob.yaml  -n ers-prod 
oc delete cm dep-restart-script -n ers-prod 
oc delete cm dep-restart-list -n ers-prod 
