oc create cm dep-restart-script --from-file=deploy.sh -n ers-prod 
oc create cm dep-restart-list --from-file=deployment.txt -n ers-prod
oc create -f cronjob.yaml  -n ers-prod
