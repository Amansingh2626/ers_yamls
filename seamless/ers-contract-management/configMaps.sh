
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap contract-management-config --from-file=config --namespace ers-prod
oc create configmap copy-contractmanagement-configs --from-file=scripts --namespace ers-prod
