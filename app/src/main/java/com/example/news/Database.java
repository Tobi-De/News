package com.example.news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private final static String DATABASE_NAME="newsdb";
    private final static int VERSION = 1;
    public final static String TABLE_BOOKMARK ="bookmark";
    public final static String KEY_ID="id";
    public final static String KEY_TITLE="title";
    public final static String KEY_URL="url";


    private static final String CREATE_PERSONNE=
            "CREATE TABLE " + TABLE_BOOKMARK + " ( "+
                    KEY_ID+" integer primary key autoincrement, "+
                    KEY_TITLE+" VARCHAR(50) not null, "+
                    KEY_URL+" VARCHAR(50) not null );";



    Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PERSONNE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOKMARK);
    }

}
