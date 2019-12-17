package com.example.news;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class UtilityClass {

    public static UtilityClass instance;
    private ArrayList<BArticle> bookmarkList = new ArrayList<>();
    private BookmarkCtrl bookmarkCtrl;
    private String tab1;
    private String tab2;
    private String tab3;
    private int actualPagePosition;

    private UtilityClass(){}

    public static UtilityClass getInstance(){
        if(instance == null){
            instance = new UtilityClass();
        }
        return instance;
    }

    public ArrayList<BArticle> getBookmarkList() {
        return bookmarkList;
    }

    public void setupBookmarkCtrl(Context context){
        bookmarkCtrl = new BookmarkCtrl(context);
        bookmarkList = bookmarkCtrl.getAllBookmarks();
    }

    public void deleteBArticleFromDb(int position){
        bookmarkCtrl.delete(bookmarkList.get(position).getId());
    }

    public BookmarkCtrl getBookmarkCtrl() {
        return  bookmarkCtrl;
    }

    public String getTab1() {
        return tab1;
    }

    public void setTab1(String tab1) {
        this.tab1 = tab1;
    }

    public String getTab2() {
        return tab2;
    }

    public void setTab2(String tab2) {
        this.tab2 = tab2;
    }

    public String getTab3() {
        return tab3;
    }

    public void setTab3(String tab3) {
        this.tab3 = tab3;
    }

    public int getActualPagePosition() {
        return actualPagePosition;
    }

    public void setActualPagePosition(int actualPagePosition) {
        this.actualPagePosition = actualPagePosition;
    }

    public static void toast(Context context, String msg){
        for(int i = 0; i < 20 ; i++) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

}
