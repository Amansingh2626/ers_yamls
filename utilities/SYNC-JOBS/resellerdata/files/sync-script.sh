
mysql -u$DBUSER -h$DB_IP -P$DB_PORT_1 $ACCDB -p$DBPASS --silent --raw  < "/others/exp_acc_data.sql" | sed 's/\t/","/g;s/^/"/;s/$/"/;s/\n//g'  > /data/account.csv
mysql -u$DBUSER -h$DB_IP -P$DB_PORT_3 $BIDB -p$DBPASS < "/others/load_acc_data.sql"

