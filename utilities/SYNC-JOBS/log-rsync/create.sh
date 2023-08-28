oc -n ers-prod create cm log-rsync-ssh-cm-id --from-file=ssh/id_rsa
oc -n ers-prod create cm log-rsync-ssh-cm-idpub --from-file=ssh/id_rsa.pub
oc -n ers-prod create cm log-rsync-ssh-cm-kh --from-file=ssh/known_hosts
oc -n ers-prod apply -f cronjob.yaml 
oc -n ers-prod apply -f cronjob-2.yaml 
