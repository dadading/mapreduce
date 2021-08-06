package ods;

import com.alibaba.fastjson.JSON;

import java.io.*;

public class TestdateCreate {
    public static void main(String[] args) throws IOException {
        //测试数据路径
        String file = args[0];

        //定义对象
        TestUser tu = new TestUser();

        //对象属性
        String[] uids = {"丁冠一", "谢海平", "丁晓燕", "张三", "李四", "null", "null", "null", "null", "丁九", "丁八", "丁七", "丁六", "丁五", "丁四", "丁三", "丁二", "丁一"};
        int[] golds = {1, 10, 100};
        int[] watchnumpvs = {9, 99, 999};
        int[] followers = {12, 23, 34};
        int[] lengths = {12, 98};

        //定义写数据流
        FileWriter fw = new FileWriter(file);

        //循环生产jeson导入文件
        for (int i = 0; i < 100000; i++) {
            //从对象属性集合随机生产一个对象
            tu.setUid(uids[(int) (Math.random() * uids.length)]);
            tu.setGold(golds[(int) (Math.random() * golds.length)]);
            tu.setWatchnumpv(watchnumpvs[(int) (Math.random() * watchnumpvs.length)]);
            tu.setFollower(followers[(int) (Math.random() * followers.length)]);
            tu.setLength(lengths[(int) (Math.random() * lengths.length)]);
            String jesonStr = JSON.toJSONString(tu);

            //写入目标文件
            fw.write(jesonStr);
            fw.write("\n");
        }

        //关闭数据流
        fw.close();
        System.out.println("测试数据生成成功--->"+file);
    }

}
