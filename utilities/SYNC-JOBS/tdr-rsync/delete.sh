oc -n ers-prod delete -f cronjob.yaml 
oc -n ers-prod delete cm rsync-ssh-cm-id
oc -n ers-prod delete cm rsync-ssh-cm-idpub
oc -n ers-prod delete cm rsync-ssh-cm-kh
