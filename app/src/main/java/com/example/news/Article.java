package com.example.news;

import androidx.annotation.NonNull;

public class Article {

    private String source;
    private String title;
    private String author;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article() {
    }

    public Article(String source, String title, String author, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
