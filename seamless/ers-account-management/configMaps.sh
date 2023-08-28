
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}




oc create configmap account-management-service-config --from-file=config --namespace ers-prod
oc create configmap account-management-service-config-templates --from-file=templates --namespace ers-prod
oc create configmap copy-account-management-service-configs --from-file=scripts --namespace ers-prod
