package videoInfo;
/**
 * 自定义数据类型
 * 主要是为了保存主播相关的核心字段，方便后期进行累加操作
 *
 */

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class VideoInfoWritable implements Writable {
    private long gold;
    private long watchnumpv;
    private long follower;
    private long length;

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getWatchnumpv() {
        return watchnumpv;
    }

    public void setWatchnumpv(long watchnumpv) {
        this.watchnumpv = watchnumpv;
    }

    public long getFollower() {
        return follower;
    }

    public void setFollower(long follower) {
        this.follower = follower;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(gold);
        dataOutput.writeLong(watchnumpv);
        dataOutput.writeLong(follower);
        dataOutput.writeLong(length);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.gold = dataInput.readLong();
        this.watchnumpv = dataInput.readLong();
        this.follower = dataInput.readLong();
        this.length = dataInput.readLong();
    }

    @Override
    public String toString() {
        return gold+"\t"+watchnumpv+"\t"+follower+"\t"+length;
    }
}
