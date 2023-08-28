


mysql -u$DBUSER -h$DB_IP -P$DB_PORT_3 $REFDB -p$DBPASS --silent --raw  < "/others/exp_reseller_data.sql" | sed 's/\t/","/g;s/^/"/;s/$/"/;s/\n//g'  > /data/refill.csv

mysql -u$DBUSER -h$DB_IP -P$DB_PORT_3 $BIDB -p$DBPASS < "/others/load_reseller_data.sql"

