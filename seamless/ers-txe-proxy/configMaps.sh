
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap txe-proxy-config --from-file=config --namespace ers-prod
oc create configmap txe-proxy-copy-config --from-file=scripts --namespace ers-prod
