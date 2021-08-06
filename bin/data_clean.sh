#!/bin/bash
source /etc/profile

# 需要把这个脚本添加到crontab中 vi /etc/crontab
# 30 12 * * * root /bin/sh +x /data/soft/jobs/data_clean.sh > /data/soft/jobs/data_clean.log

echo "任务开始:"`date`

#判断用户是否输入日期参数,如果没有输入则默认获取昨天日期
if [ "X$1" = "X" ]
then
  yes_time=`date +%Y%m%d --date="1 days ago"`
else
  yes_time=$1
fi

ods_date_output_path=/data/videoinfo/ods/${yes_time}
ods_date_output_file=/data/videoinfo/ods/${yes_time}/users.del

cleanjob_input=hdfs://hadoop002:9000/data/videoinfo/ods_data/${yes_time}
cleanjob_output=hdfs://hadoop002:9000/data/videoinfo/clean_data/${yes_time}

videoinfojob_input=${cleanjob_output}
videoinfojob_output=hdfs://hadoop002:9000/data/videoinfo/res1_data/${yes_time}

videoinfojobtop10_input=${cleanjob_output}
videoinfojobtop10_output=hdfs://hadoop002:9000/data/videoinfo/res2_data/${yes_time}

jobs_home=/data/soft/jobs

#创建ods文件
rm -rf ${ods_date_output_path}
rm -rf ${ods_date_output_file}
mkdir -p ${ods_date_output_path}
touch ${ods_date_output_file}

# 删除输出目录,为了兼容脚本重跑的情况
hdfs dfs -rm -r ${cleanjob_input}
hdfs dfs -rm -r ${cleanjob_output}
hdfs dfs -rm -r ${videoinfojob_output}
hdfs dfs -rm -r ${videoinfojobtop10_output}
hdfs dfs -mkdir -p ${cleanjob_input}

#生成ods数据
java -classpath \
${jobs_home}/mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \
ods.TestdateCreate \
${ods_date_output_file}

#将生成的ods数据上传hdfs
hadoop fs -put ${ods_date_output_file} ${cleanjob_input}

#执行数据清洗任务
hadoop jar \
${jobs_home}/mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \
dataClean.DateCleanMap \
${cleanjob_input} \
${cleanjob_output}

# 判断数据清洗任务是否执行成功
hdfs dfs -ls ${cleanjob_output}/_SUCCESS
if [ "$?" = "0" ]
then
  echo "cleanJob execute sucess..."
    #执行指标统计任务1
    hadoop jar \
    ${jobs_home}/mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \
    videoInfo.VideoInfoJob \
    ${videoinfojob_input} \
    ${videoinfojob_output}
    hdfs dfs -ls ${videoinfojob_output}/_SUCCESS
    if [ "$?" = "0" ]
    then
      echo "VideoInfoJob execute sucess..."
    else
      echo "VideoInfoJob execute faild... data time is ${yes_time}"
      #调用告警接口
    fi

    #执行指标统计任务2
    hadoop jar \
    ${jobs_home}/mapreduce-1.0-SNAPSHOT-jar-with-dependencies.jar \
    top10.VideoInfoJobTop10 \
    ${videoinfojobtop10_input} \
    ${videoinfojobtop10_output}
    hdfs dfs -ls ${videoinfojobtop10_output}/_SUCCESS
    if [ "$?" = "0" ]
    then
      echo "VideoInfoJobTop10 execute sucess..."
    else
      echo "VideoInfoJobTop10 execute faild... data time is ${yes_time}"
      #调用告警接口
    fi
else
  echo "cleanJob execute faild... data time is ${yes_time}"
  #调用告警接口
fi