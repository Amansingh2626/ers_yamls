
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}



#tar -cf conf.tar -C config .
#tar -cf transformers.tar transformers
#tar -cf spring-contexts.tar spring-contexts
#oc create configmap ers-standard-link-config --from-file=conf.tar --namespace ers-prod
#oc create configmap ers-standard-link-transformers --from-file=transformers.tar --namespace ers-prod
#oc create configmap ers-standard-link-spring-contexts --from-file=spring-contexts.tar --namespace ers-prod


#rm -f conf.tar  spring-contexts.tar transformers.tar

##Aman
tar cfz conf.tar.gz -C config .
oc create configmap ers-standard-link-config --from-file=conf.tar.gz --namespace ers-prod
rm -f conf.tar.gz


rm -f test-config-lib.tar


