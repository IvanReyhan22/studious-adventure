package com.ezyindustries.goes_englishcourse.RecyclerView;

public class refData {

    String title;
    String url;

    public  refData(){}

    public refData(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

