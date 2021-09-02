import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.util.*;

public class Test {
    public static void main(String[] args) {
//        //json数据测试
//        //通过原生生成json数据格式
//        JSONObject zhangsan = new JSONObject();
//        //添加
//        zhangsan.put("name", "张三");
//        zhangsan.put("age", 18);
//        zhangsan.put("birthday", "1900-01-01");
//        zhangsan.put("majar", new String[]{"数学", "英文"});
//        zhangsan.put("house", null);
//        zhangsan.put("isreal", true);
//        System.out.println(zhangsan.toString());
//
//        //提取所需信息
//        String name = zhangsan.getString("name");
//        System.out.println("----------");
//
//        //通过hashmap数据结构生成
//        HashMap<String,Object> zhangsan2 = new HashMap<>();
//        //添加
//        zhangsan2.put("name", "张三");
//        zhangsan2.put("age", 18);
//        zhangsan2.put("birthday", "1900-01-01");
//        zhangsan2.put("majar", new String[]{"数学", "英文"});
//        zhangsan2.put("house", null);
//        zhangsan2.put("isreal", true);
//        System.out.println(new JSONObject(zhangsan2).toString());
//
//        //把hashmap对象转为json对象
//        JSONObject zhangsan2Json = new JSONObject(zhangsan2);
//        //提取所需信息
//        String name2 = zhangsan2Json.getString("name");
//        System.out.println("----------");
//
//        //通过实体生成
//        TestUser tu = new TestUser();
//        tu.setUid("dingguanyi");
//        tu.setGold(100);
//        tu.setLength(99);
//        tu.setFollower(100);
//        tu.setWatchnumpv(0);
//        //生成json格式
//        String str1 = JSON.toJSONString(tu);
//        System.out.println(JSON.toJSON(tu));
//        //对象转成String
//        String str2 = JSONObject.toJSONString(tu);
//        System.out.println(JSONObject.toJSONString(tu));
//
//        //把实体对象转为json对象
//        JSONObject tu2 = new JSONObject();
//        //提取所需信息
//        System.out.println("----------");
//
//
//        //HashMap排序
//        Map<String,Long> map = new HashMap<String,Long>();
//        map.put("dingguanyi",30L);
//        map.put("xiehaiping",27L);
//        map.put("dingxiaoyan",33L);
//        System.out.println(map.entrySet());
//
//        //将map.entrySet()转化为List
//        List<Map.Entry<String,Long>> list = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
//        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
//            //降序排序
//            @Override
//            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//
//        System.out.println(list);
//
//        for(Map.Entry<String,Long> mapping:list){
//            System.out.println(mapping.getKey()+":"+mapping.getValue());
//        }

//        ArrayList list2 = new ArrayList();
//        list2.add("dingguanyi");
//        list2.add("123");
//        list2.add("456");
//        System.out.println(list2);
//
//        //遍历1
//        for(Object o : list2){
//            System.out.println((String)o);
//        }
//
//        //遍历2
//        Iterator ite = list2.iterator();
//        while(ite.hasNext()){
//            String s1 = (String)ite.next();
//            System.out.println(s1);
//        }

        String s = args[0];
        System.out.println(s);
    }
}
