LOG_PATH="/var/seamless/log/notification-language-preference"
TEMP_FILE=${LOG_PATH}/source/daily_reference_$(date +"%d-%m-%Y.csv.tmp");
LOG_FILE=${LOG_PATH}/log/daily_reference_log_$(date +%d-%m-%Y).log;
FILENAME=daily_reference_$(date +"%d-%m-%Y.csv");
FILE_NOT_FOUND=false

mkdir -p ${LOG_PATH}/source/
mkdir -p ${LOG_PATH}/log/
mkdir -p ${LOG_PATH}/backup/

# NMS_HOST      -- Default localhost:8277
# NMS_PORT      -- Default 8277
# RECIPIENT_IDS -- temp@seamless.se
# SENDER_MAIL   -- temp@seamless.se
# REMOTE_PATH   --
# USER          --
# IP            --
# PASSWORD      --
# PORT          --
#================================================================================================================
function logger()
{
my_date=`date +%d%m%Y-\|-%H:%M:%S`
printf '>> %-6s = %-19s -- %s\n' "$1" "$my_date" "$2" >> ${LOG_FILE}
}
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function move_older()
{
#Move Previous file which is exist in source directory
if [ -e ${LOG_PATH}/source/daily_reference_* ]
then
    mv ${LOG_PATH}/source/daily_reference_* ${LOG_PATH}/backup/
    logger INFO "Previous file exist! Moved to backup folder"
else
    logger ERROR "Previous file does not exist!"
fi
}
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function sftp_files()
{
#Using SFTP to download the file from remote server
logger INFO "Ready to download given file from remote server. FileName :  language_preference_daily_incremental_$(date +%d-%m-%Y).csv"

echo "ls ${REMOTE_PATH}/Language_Preference_Daily_Incrimental_$(date +%d-%m-%Y).csv" | sshpass -p "${PASSWORD}" sftp -P ${PORT}  -oStrictHostKeyChecking=no  -b - ${USER}@${IP}


if [ $? -eq 0 ]
then
    logger INFO "Files exist, can download now"
    echo "get ${REMOTE_PATH}/Language_Preference_Daily_Incrimental_$(date +%d-%m-%Y).csv ${LOG_PATH}/source/daily_reference_$(date +%d-%m-%Y).csv.tmp" | sshpass -p "${PASSWORD}" sftp -P ${PORT}  -oStrictHostKeyChecking=no  -b - ${USER}@${IP}

    if [ $? -eq 0 ]
    then
        logger INFO "Files successfully downloaded"
        sleep 2;

        logger INFO "Start to modify the file which is downloaded to the remote server."
        # Added START flag in CSV 2 line.
        sed -i 2i"START" ${TEMP_FILE};

        # Added END flag in CSV last line.
        sed -i -e '$aEND' ${TEMP_FILE};
        logger INFO ":: Modified completed ::"

        # Transfer TEMP_FILE data to original file.
        cat ${TEMP_FILE} >> ${LOG_PATH}/source/${FILENAME};
        logger INFO "Converted tmp file to csv file."

        # Remove TEMP_FILE
        rm ${TEMP_FILE};
        logger INFO "Removed tmp file."
    else
        FILE_NOT_FOUND=true
        logger INFO "Files exist, but failed to download"
    fi
else
    FILE_NOT_FOUND=true
    logger ERROR "Files do not exist."
fi

if ${FILE_NOT_FOUND}
then
  logger INFO "Notification API called started"
  echo RESULT=$(curl --location --request POST "http://${NMS_HOST:-localhost}:${NMS_PORT:-8277}/register" --header 'Content-Type: application/json' --data '{
       "eventId": "sdsLangPref",
       "eventTag": "ADHOC_ALERT",
       "fields": {
           "recipientId": "'${RECIPIENT_IDS:-temp@seamless.se}'",
           "senderId": "'${SENDER_MAIL:-temp@seamless.se}'",
           "message": "File '${REMOTE_PATH}/Language_Preference_Daily_Incrimental_$(date +%d-%m-%Y).csv' not found or can'\''t reach Server",
           "notificationType": "EMAIL",
           "referenceEventId": "REF_ID",
           "EMAILPROPS": {
               "SUBJECT":"Notification-Preference"
           }
       }
   }')
else
    logger INFO "Notification API call not needed"
fi

}
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
MYDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd ${MYDIR}


logger INFO  "Notification preference daily script started : $(date)"
move_older
#logger INFO "Ready to download given file from remote server. FileName : ${FILENAME}"
sftp_files
logger INFO "Notification preference daily script ended :  $(date)"
