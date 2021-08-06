import com.alibaba.fastjson.JSON;

public class FastjsonDemo {
    public static void main(String[] args) {
        Group group = new Group();
        group.setId(0L);
        group.setName("admin");

        User guestUser = new User();
        guestUser.setId(2L);
        guestUser.setName("guest");

        User rootUser = new User();
        rootUser.setId(3L);
        rootUser.setName("root");

        group.addUser(guestUser);
        group.addUser(rootUser);

        //对象转化为json字符串
        String jsonString = JSON.toJSONString(group);
        System.out.println(jsonString);

        //字符串转化为对象
        String jsonString2 = "{\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}";
        Group group2 = JSON.parseObject(jsonString2,Group.class);
        System.out.println(group2.getUsers().get(0).getName());
    }
}
