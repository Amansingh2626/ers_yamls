oc  -n ers-prod create secret generic es-exp-secret --from-file=ca.crt
oc  -n ers-prod create -f es-exporter.yaml 
