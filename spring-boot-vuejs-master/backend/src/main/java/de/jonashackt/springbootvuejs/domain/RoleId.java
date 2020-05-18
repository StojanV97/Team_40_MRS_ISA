package de.jonashackt.springbootvuejs.domain;

public class RoleId {

    private String role;
    private Long userID;
    //private Integer adminOf;

    public RoleId() {}

    public RoleId(String role, Long userID) {
        this.role = role;
        this.userID = userID;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /*public Integer getAdminOf() {
        return adminOf;
    }

    public void setAdminOf(Integer adminOf) {
        this.adminOf = adminOf;
    }
    */
}
