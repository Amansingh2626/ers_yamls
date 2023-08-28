
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap bi-engine-config --from-file=config --namespace ers-prod
oc create configmap bi-engine-scripts --from-file=scripts --namespace ers-prod
