package de.jonashackt.springbootvuejs.domain;

public class UserRole {

    private String role;
    private User user;
    //private Integer adminOf;

    public UserRole() {}

    public UserRole(String role, User userID) {
        this.role = role;
        this.user = userID;
    }

    /*

    public RoleId(String role, Long userID, Integer adminOf) {
        this.role = role;
        this.userID = userID;
        this.adminOf = adminOf;
    }*/

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUserID() {
        return user;
    }

    public void setUserID(User user) {
        this.user = user;
    }

    /*public Integer getAdminOf() {
        return adminOf;
    }

    public void setAdminOf(Integer adminOf) {
        this.adminOf = adminOf;
    }
    */
}
