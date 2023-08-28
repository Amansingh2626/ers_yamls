
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



oc create configmap dealer-management-system-config --from-file=config --namespace ers-prod
oc create configmap dealer-management-system-config-templates --from-file=templates --namespace ers-prod
oc create configmap copy-dealer-management-configs --from-file=scripts --namespace ers-prod


sed -i 's+on.startup.cache.resellers=true+on.startup.cache.resellers=false+g' config/dealer-management-system.properties
oc create configmap dealer-management-system-config-no-cache --from-file=config --namespace ers-prod
sed -i 's+on.startup.cache.resellers=false+on.startup.cache.resellers=true+g' config/dealer-management-system.properties
