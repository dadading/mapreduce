package top10;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class VideoInfoJobTop10Reduce extends Reducer<Text, LongWritable, Text, LongWritable> {
    //保存主播的uid和开播总时长
    HashMap<String, Long> map = new HashMap<String, Long>();

    @Override
    protected void reduce(Text k2, Iterable<LongWritable> v2s, Context context) throws IOException, InterruptedException {
        //求每个主播总直播时长
        long lengthSum = 0;
        for (LongWritable v2 : v2s) {
            lengthSum += v2.get();
        }
        //将主播uid/总直播时长存储在map中
        map.put(k2.toString(), lengthSum);
    }

    /**
     * 任务初始化的时候执行一次，仅执行一次，一般在里面做一些初始化资源链接的动作
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    /**
     * 任务结束的时候执行一次，仅执行一次，做一些关闭资源的操作
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
       //从配置类中获取dt参数
        Configuration conf = context.getConfiguration();
        String dt = conf.get("dt");

        Map<String, Long> sortedMap = MapUtils.sortValue(map);
        Set<Map.Entry<String, Long>> entries = sortedMap.entrySet();
        Iterator<Map.Entry<String, Long>> it = entries.iterator();
        int count = 1;
        while (count <= 10 && it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            String key = entry.getKey();
            Long value = entry.getValue();

            //封装k3,v3
            Text k3 = new Text();
            k3.set(dt+"\t"+key);
            LongWritable v3 = new LongWritable();
            v3.set(value);

            //写出数据
            context.write(k3,v3);

            count++;
        }
    }
}
