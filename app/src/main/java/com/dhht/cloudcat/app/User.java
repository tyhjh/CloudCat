package com.dhht.cloudcat.app;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author HanPei
 * @date 2019/2/19  下午3:36
 */
@Data
@EqualsAndHashCode
public class User {
    int id;

    public User(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {

        //判断是否等于自身.
        if (obj == this) {
            return true;
        }

        //判断是否为User对象，也会判断是否为null
        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        //判断值是否相等
        if (user.getId() != id) {
            return true;
        }

        return false;
    }
}
