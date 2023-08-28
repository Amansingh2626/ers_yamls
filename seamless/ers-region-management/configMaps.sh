
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap region-management-core-config --from-file=config --namespace ers-prod
oc create configmap region-management-core-config-templates --from-file=templates --namespace ers-prod
oc create configmap copy-region-management-configs --from-file=scripts --namespace ers-prod
