
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap autotransfer-manager-config --from-file=config --namespace ers-prod
oc create configmap autotransfer-manager-template --from-file=template --namespace ers-prod
oc create configmap autotransfer-copy-script --from-file=scripts --namespace ers-prod
