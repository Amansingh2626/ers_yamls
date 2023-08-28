
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap ldaplink-config   --from-file=config --namespace ers-prod
oc create configmap ldaplink-scripts   --from-file=scripts --namespace ers-prod
