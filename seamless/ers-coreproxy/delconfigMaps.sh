oc delete configmap coreproxy-config  --namespace ers-prod
oc delete configmap core-config  --namespace ers-prod
oc delete configmap core-config-defaults --namespace ers-prod
oc delete configmap core-config-jetm  --namespace ers-prod
oc delete configmap core-config-langmaps  --namespace ers-prod
oc delete configmap core-config-log4j --namespace ers-prod
oc delete configmap copy-coreproxy-configs --namespace ers-prod
