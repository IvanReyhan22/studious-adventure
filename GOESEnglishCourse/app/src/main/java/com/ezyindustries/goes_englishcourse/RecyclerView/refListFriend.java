package com.ezyindustries.goes_englishcourse.RecyclerView;

public class refListFriend {

    String username;
    String deskripsion;
    String url;

    public refListFriend() {
    }

    public refListFriend(String username, String deskripsion, String url) {
        this.username = username;
        this.deskripsion = deskripsion;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
