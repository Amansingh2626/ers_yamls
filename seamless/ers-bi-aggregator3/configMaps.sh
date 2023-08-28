MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap bi-aggregator3-config --from-file=config --namespace ers-prod
oc create configmap bi-aggregator3-mapping --from-file=config/mapping  --namespace ers-prod
oc create configmap scripts3-config --from-file=scripts --namespace ers-prod
