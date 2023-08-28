
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap product-management-config --from-file=config --namespace ers-prod
oc create configmap product-management-templates --from-file=templates --namespace ers-prod
oc create configmap copy-product-management-configs --from-file=scripts --namespace ers-prod
