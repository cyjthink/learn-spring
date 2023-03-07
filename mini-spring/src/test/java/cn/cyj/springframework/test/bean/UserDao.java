package cn.cyj.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("1", "111");
        hashMap.put("2", "222");
        hashMap.put("3", "333");
    }

    public void initData() {
        System.out.println(">>UserDao#initData call<<");
        hashMap.put("4", "444");
    }

    public void clearData() {
        System.out.println(">>UserDao#clearData call<<");
        hashMap.clear();
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
