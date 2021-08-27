package com.android.test.data;

import java.util.Objects;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-29   16:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UserData {
    public String userName;
    public int age;

    public UserData(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return age == userData.age &&
                userName.equals(userData.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, age);
    }
}
