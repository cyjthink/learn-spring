package cn.cyj.springframework.test.bean;

import cn.cyj.springframework.beans.factory.DisposableBean;
import cn.cyj.springframework.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {

    private String uId;
    private String company;
    private String location;

    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("UserService{" +
                "uId='" + uId + '\'' +
                ", name=" + userDao.queryUserName(uId) + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                '}');
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(">>UserService#destroy() call<<");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(">>UserService#afterPropertiesSet() call<<");
    }
}
