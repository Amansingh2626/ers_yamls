sed -i "s+access\.log+access-$(echo $POD_NAME|rev|cut -d'-' -f1 |rev)\.log+g" /etc/nginx/nginx.conf
