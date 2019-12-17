package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class BookmarkCtrl {

    private final Context context;
    private Database dataBase;
    private SQLiteDatabase db;

    public BookmarkCtrl(Context context) {
        this.context = context;
        dataBase = new Database(context);
    }

    private void close(){
        db=dataBase.getReadableDatabase();
        if(db !=null && db.isOpen()){
            db.close();
        }

    }

    public boolean insert(BArticle article){
        long id=0;
        db = dataBase.getWritableDatabase();

        ContentValues params = new ContentValues();
        //params.put(DataBase.KEY_ID, idp);
        params.put(Database.KEY_TITLE, article.getTitle());
        params.put(Database.KEY_URL, article.getUrl());

        id=db.insert(Database.TABLE_BOOKMARK,null,params);
        close();

        return id >0;
    }


    public boolean update(long rowid, BArticle article){
        db = dataBase.getWritableDatabase();

        ContentValues params = new ContentValues();
        params.put(Database.KEY_TITLE, article.getTitle());
        params.put(Database.KEY_URL, article.getUrl());
        //params.put(DataBase.KEY_NOM, nom);

        long result = 0;
        result= db.update(Database.TABLE_BOOKMARK,params,Database.KEY_ID+"="+rowid,null);
        close();

        return result>0;

    }

    public boolean delete(long rowid){
        db=dataBase.getReadableDatabase();
        long result =0;
        result= db.delete(Database.TABLE_BOOKMARK,Database.KEY_ID+ "="+rowid,null);
        close();
        return result>0;
    }

    public ArrayList<BArticle> getAllBookmarks(){
        ArrayList<BArticle> personnes = new ArrayList<>();
        db=dataBase.getReadableDatabase();
        String query="SELECT * FROM "+Database.TABLE_BOOKMARK +";";

        Cursor c = db.rawQuery(query,null);

        if(c != null){
            if(c.moveToFirst()){
                do{
                    int id = c.getInt(c.getColumnIndex(Database.KEY_ID));
                    String title = c.getString(c.getColumnIndex(Database.KEY_TITLE));
                    String url = c.getString(c.getColumnIndex(Database.KEY_URL));
                    personnes.add(new BArticle(id, url, title));
                }while(c.moveToNext());
            }
        }
        return personnes;
    }

    //counts number of element of the database
    public int getLastId(){
        db = dataBase.getReadableDatabase();
        String req="SELECT * FROM "+ Database.TABLE_BOOKMARK;
        Cursor cursor = db.rawQuery(req, null);
        return cursor.getCount();
    }

}
