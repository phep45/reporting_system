package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

public class User {

    private int userId;
    private String userName;

    public User() {
        this(0,"");
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        return userName != null ? userName.equals(user.userName) : user.userName == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
