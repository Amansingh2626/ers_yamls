#!/bin/bash
#u -- Usage: sh script chart-name

if [[ $# -eq 0 ]]
  then
    echo "Wrong arguments passed"
    grep "^#u" $0 | sed -e "s+^#u++" | sed   "s+script+$0+g"
    exit 1;
fi
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}


if [[ ${1} == "all" ]]
then
for dir in `find . -maxdepth 1 -type d  -name 'ers-*' -not -path "./ers-common" -not -path "."| sort`
do 
cd ${dir} 
sh configMaps.sh           
cd ..
for i in $(ls ${dir}/02*.yaml );
do 
kubectl apply -f $i
done
done


else
for var in "$@"
do
cd ${var} 
sh configMaps.sh           
for i in $(ls 02*.yaml );
do 
kubectl apply -f $i
done
cd ..
done
fi

