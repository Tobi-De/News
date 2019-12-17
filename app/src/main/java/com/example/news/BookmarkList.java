package com.example.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BookmarkList extends AppCompatActivity{

    ArrayAdapter<BArticle> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);
        ListView list = findViewById(R.id.listB);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, UtilityClass.getInstance().getBookmarkList());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BArticle choosen = UtilityClass.getInstance().getBookmarkList().get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getApplicationContext() , ArticleContent.class);
                bundle.putString("url", choosen.getUrl());
                bundle.putString("title", choosen.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(BookmarkList.this);
                builder.setTitle("Remove Bookmark");
                builder.setMessage("Do you really want to delete this bookmark ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UtilityClass.getInstance().deleteBArticleFromDb(position);
                        UtilityClass.getInstance().getBookmarkList().remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.show();
                return true;
            }
        });
    }
}
