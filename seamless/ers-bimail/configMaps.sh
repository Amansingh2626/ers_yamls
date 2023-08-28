
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}

tar -cf conf.tar -C config .
tar -cf mail.tar mail
oc create configmap ers-bimail-config --from-file=conf.tar --namespace ers-prod
oc create configmap ers-bimail-mail --from-file=mail.tar --namespace ers-prod

rm -f conf.tar  mail.tar

