package videoInfo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 需求：实现求每位主播当天获得的gold最大值
 */

public class GoldMaxCount {
    public static class MyMapper extends Mapper<LongWritable, Text, Text, Text> {
        /**
         * k1是主播uid v1是输入的属性字符串
         *
         * @param k1
         * @param v1
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            //提取uid
            String uid = v1.toString().split("\t")[0];

            //提取gold属性
            String gold = v1.toString().split("\t")[1];

            //组合k2 v2
            Text k2 = new Text(uid);
            Text v2 = new Text(gold);

            //写出k2 v2
            context.write(k2, v2);
        }
    }

    public static class MyReduce extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text k2, Iterable<Text> v2s, Context context) throws IOException, InterruptedException {
            //约定初始化最大值是-1
            Long goldMax = -1L;
            //获取gold最大值
            for (Text t : v2s) {
                Long nowGold = Long.parseLong(t.toString());
                if (nowGold > goldMax) {
                    goldMax = nowGold;
                }
            }

            //组合k3 v3
            String goldMaxStr = goldMax + "";
            Text k3 = new Text(k2);
            Text v3 = new Text(goldMaxStr);

            //写出k3 v3
            context.write(k3, v3);
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                //如果程序传递的参数不够,则程序直接退出
                System.exit(100);
            }

            //job需要的配置参数
            Configuration conf = new Configuration();
            //创建一个job
            Job job = Job.getInstance(conf);

            //注意：这一行必须设置
            job.setJarByClass(GoldMaxCount.class);

            //指定输入路径(可以是文件也可以是目录)
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            //指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //指定map相关的代码
            job.setMapperClass(GoldMaxCount.MyMapper.class);
            //指定k2的类型
            job.setMapOutputKeyClass(Text.class);
            //指定v2的类型
            job.setMapOutputValueClass(Text.class);

            //指定reduce相关代码
            job.setReducerClass(GoldMaxCount.MyReduce.class);
            //指定k3的类型
            job.setOutputKeyClass(Text.class);
            //指定v3的类型
            job.setOutputValueClass(Text.class);

            //提交job
            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
