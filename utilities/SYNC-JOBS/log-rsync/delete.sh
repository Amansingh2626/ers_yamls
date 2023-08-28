oc -n ers-prod delete -f cronjob.yaml 
oc -n ers-prod delete -f cronjob-2.yaml 
oc -n ers-prod delete cm log-rsync-ssh-cm-id
oc -n ers-prod delete cm log-rsync-ssh-cm-idpub
oc -n ers-prod delete cm log-rsync-ssh-cm-kh
