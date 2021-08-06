package top10;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 实现自定义map类，这里实现核心字段的拼接
 */

public class VideoInfoJobTop10Map extends Mapper<LongWritable, Text, Text, LongWritable> {
    @Override
    protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
        //读取清洗后的每行数据
        String line = v1.toString();
        //使用制表符切割数据
        String[] fields = line.split("\t");
        String uid = fields[0];
        long length = Long.parseLong(fields[4]);

        //组装k2 v2
        Text k2 = new Text(uid);
        LongWritable v2 = new LongWritable(length);

        //写出k2 v2
        context.write(k2, v2);
    }
}
