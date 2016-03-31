package com.luxoft.jmswithspring.model;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@TableName("User")
@XmlRootElement
public class User {

    private int userId;
    private String userName;
    private String surname;
    private String birthDate;

    public User(){}

    @Deprecated
    /**
     * Use builder instead
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static Builder builder() {
        return new User().new Builder();
    }

    public int getUserId() {
        return userId;
    }

    @XmlElement(name = "user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    @XmlElement(name = "f_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    @XmlElement(name = "s_name")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @XmlElement(name = "br_date")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public class Builder {
        private Builder() {}

        public Builder withUserId(int id) {
            User.this.userId = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            User.this.userName = firstName;
            return this;
        }

        public Builder withSurname(String surname) {
            User.this.surname = surname;
            return this;
        }

        public Builder withBirthDate(String birthDate) {
            User.this.birthDate = birthDate;
            return this;
        }

        public User build() {
            return User.this;
        }

    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("surname", surname)
                .add("birthDate", birthDate)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        return birthDate != null ? birthDate.equals(user.birthDate) : user.birthDate == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
