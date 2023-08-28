sh configMaps.sh
oc -n ers-prod apply -f 02-others-deployment.yaml
oc -n ers-prod apply -f 02-txlog-statefulset.yaml
