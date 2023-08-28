
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap commission-engine-config --from-file=config --namespace ers-prod
