


mysql -u$DBUSER -h$DB_IP -P$DB_PORT_1 $ACCDB -p$DBPASS --silent --raw  < "/others/exp_txn_data.sql" | sed 's/\t/","/g;s/^/"/;s/$/"/;s/\n//g'  > /data/txn_data.csv

mysql -u$DBUSER -h$DB_IP -P$DB_PORT_3 $BIDB -p$DBPASS < "/others/load_txn_data.sql"

