MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap subscribersystem-config --from-file=config --namespace ers-prod
oc create configmap copy-subscribersystem-configs    --from-file=scripts --namespace ers-prod   