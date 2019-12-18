package com.example.news;

import java.util.ArrayList;

public class ArticleListCache {
    private ArrayList<Article> articlesList;
    private String URL;


    public ArticleListCache(ArrayList<Article> articlesList, String URL) {
        this.articlesList = articlesList;
        this.URL = URL;
    }

    public ArrayList<Article> getArticlesList() {
        return articlesList;
    }

    /*public void setArticlesList(ArrayList<Article> articlesList) {
        this.articlesList = articlesList;
    }*/

    public String getURL() {
        return URL;
    }

    /*public void setURL(String URL) {
        this.URL = URL;
    }*/
}
