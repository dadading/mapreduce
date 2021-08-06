package top10;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import videoInfo.VideoInfoMap;
import videoInfo.VideoInfoReduce;

/**
 * 数据指标统计作业
 * 需求：
 * 2：统计每天开播时长最长的前10名主播及对应的主播时长
 *
 * 分析：
 * 1：为了统计每天开播最长的前10名主播信息，需要在map阶段取数中的每个主播id和直播时长
 * 2：所以在map阶段的<k2,v2>是<Text,LongWritable>
 * 3：在Reduce端对相同主播的直播时长进行累加求和，把这些数据存在一个临时map集合中
 * 4：在Reduce端的cleanup函数中对map集合中的数据根据直播时长排序
 * 5：最后在cleanup函数中把直播时长最长的前10名主播信息写到hdfs文件中0
 *
 */

public class VideoInfoJobTop10 {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                //如果程序传递的参数不够,则程序直接退出
                System.exit(100);
            }

            //job需要的配置参数
            Configuration conf = new Configuration();

            //从输入路径中获取日期
            String[] fields = args[0].split("/");
            String tmpdt = fields[fields.length-1];
            String dt = DateUtils.transDateFormat(tmpdt);
            conf.set("dt",dt);

            //创建一个job
            Job job = Job.getInstance(conf);

            //注意：这一行必须设置
            job.setJarByClass(VideoInfoJobTop10.class);

            //指定输入路径(可以是文件也可以是目录)
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            //指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //指定map相关的代码
            job.setMapperClass(VideoInfoJobTop10Map.class);
            //指定k2的类型
            job.setMapOutputKeyClass(Text.class);
            //指定v2的类型
            job.setMapOutputValueClass(LongWritable.class);

            //指定reduce相关代码
            job.setReducerClass(VideoInfoJobTop10Reduce.class);
            //指定k3的类型
            job.setOutputKeyClass(Text.class);
            //指定v3的类型
            job.setOutputValueClass(LongWritable.class);

            //提交job
            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
