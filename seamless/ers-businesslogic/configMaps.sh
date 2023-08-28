
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap businesslogic-config --from-file=config --namespace ers-prod
oc create configmap copy-business-configs --from-file=scripts --namespace ers-prod