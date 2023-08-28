
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap coreproxy-config --from-file=config --namespace ers-prod
oc create configmap core-config --from-file=core --namespace ers-prod
oc create configmap core-config-defaults --from-file=core/defaults --namespace ers-prod
oc create configmap core-config-jetm --from-file=core/jetm --namespace ers-prod
oc create configmap core-config-langmaps --from-file=core/langmaps --namespace ers-prod
oc create configmap core-config-log4j --from-file=core/log4j --namespace ers-prod
oc create configmap copy-coreproxy-configs --from-file=scripts --namespace ers-prod
