
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap uciplink-config --from-file=config --namespace ers-prod
oc create configmap copy-uciplink-config --from-file=scripts --namespace ers-prod
