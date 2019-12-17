package com.example.news;

import androidx.annotation.NonNull;

public class BArticle {
    String url;
    String title;
    int id;

    public BArticle(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public BArticle(int id, String url, String title) {
        this.url = url;
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
