package com.ezyindustries.goes_englishcourse.RecyclerView;

public class refListFriend {

    String username;
    String deskripsion;
    String picUrl;

    public refListFriend() {
    }

    public refListFriend(String username, String deskripsion, String picUrl) {
        this.username = username;
        this.deskripsion = deskripsion;
        this.picUrl = picUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeskripsion() {
        return deskripsion;
    }

    public void setDeskripsion(String deskripsion) {
        this.deskripsion = deskripsion;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
