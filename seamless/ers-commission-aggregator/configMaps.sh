
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap commission-aggregator-config --from-file=config --namespace ers-prod
oc create configmap commission-aggregator-scripts --from-file=scripts --namespace ers-prod
