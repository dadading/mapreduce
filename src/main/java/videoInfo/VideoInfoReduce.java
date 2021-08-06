package videoInfo;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class VideoInfoReduce extends Reducer<Text,VideoInfoWritable,Text,VideoInfoWritable> {
    @Override
    protected void reduce(Text k2, Iterable<VideoInfoWritable> v2s, Context context) throws IOException, InterruptedException {
        //从v2s中把相同key的value取出来，进行累加求和
        long goldSum = 0;
        long watchnumpvSum = 0;
        long followerSum = 0;
        long lengthSum = 0;

        for(VideoInfoWritable v2:v2s){
            goldSum+=v2.getGold();
            watchnumpvSum+=v2.getWatchnumpv();
            followerSum+=v2.getFollower();
            lengthSum+=v2.getLength();
        }

        //组装k3 v3
        Text k3 = k2;
        VideoInfoWritable v3 = new VideoInfoWritable();
        v3.setGold(goldSum);
        v3.setWatchnumpv(watchnumpvSum);
        v3.setFollower(followerSum);
        v3.setLength(lengthSum);

        //写出k3 v3
        context.write(k3,v3);
    }
}
