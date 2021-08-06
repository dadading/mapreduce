package dataClean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 实现自定义的Map类
 */

public class DateCleanMap extends Mapper<LongWritable, Text, Text, Text> {

    /**
     * 1.从原始数据中过滤出来需要的字段
     * 2.针对核心字段进行异常值判断
     *
     * @param k1
     * @param v1
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
        //将输入的数据转为json对象
        JSONObject jsonObj = JSON.parseObject(v1.toString());
        //从json对象提取对象的属性
        String uid = jsonObj.getString("uid").trim();
        int gold = jsonObj.getIntValue("gold");
        int watchnumpv = jsonObj.getIntValue("watchnumpv");
        int follower = jsonObj.getIntValue("follower");
        int length = jsonObj.getIntValue("length");

        //过滤异常数据
        if (!uid.equals("null") && gold >= 0 && watchnumpv >= 0 && follower >= 0 && length >= 0) {
            //组装k2v2
            Text k2 = new Text(uid);
            Text v2 = new Text(gold + "\t" + watchnumpv + "\t" + follower + "\t" + length);
            context.write(k2, v2);
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
            job.setJarByClass(DateCleanMap.class);

            //指定输入路径(可以是文件也可以是目录)
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            //指定输出路径(只能指定一个不存在的目录)
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //指定map相关的代码
            job.setMapperClass(DateCleanMap.class);
            //指定k2的类型
            job.setMapOutputKeyClass(Text.class);
            //指定v2的类型
            job.setMapOutputValueClass(Text.class);

            //禁用reduce
            job.setNumReduceTasks(0);

            //提交job
            job.waitForCompletion(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
