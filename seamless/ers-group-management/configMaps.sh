
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap group-management-config --from-file=config --namespace ers-prod
oc create configmap group-management-templates-config --from-file=templates --namespace ers-prod
oc create configmap copy-group-management-configs --from-file=scripts --namespace ers-prod
