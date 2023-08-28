oc -n ers-prod delete -f 02-others-deployment.yaml
oc -n ers-prod delete -f 02-txlog-statefulset.yaml
sh delConfigMaps.sh
