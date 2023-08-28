
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}


oc create configmap access-management-config --from-file=config --namespace ers-prod
oc create configmap access-management-templates-config --from-file=templates --namespace ers-prod
oc create configmap copy-access-management-configs --from-file=scripts --namespace ers-prod
