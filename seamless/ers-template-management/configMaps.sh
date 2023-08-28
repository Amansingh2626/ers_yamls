
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap template-management-system-config --from-file=config --namespace ers-prod
oc create configmap copy-template-management-config --from-file=scripts --namespace ers-prod