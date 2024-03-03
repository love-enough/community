package com.newcoder.community.util;

import com.newcoder.community.entity.User;
import org.springframework.stereotype.Component;

@Component
/**
 * @Description 持有用户信息,代替session对象
 * @author GuoZihan
 * @date 21:28 2024/3/3
 */
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }
    public User getUser() {
        return users.get();
    }
    public void clear() {
        users.remove();
    }
}
