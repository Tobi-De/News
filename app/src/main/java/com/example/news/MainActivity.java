package com.example.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.news.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements SimpleSearchDialog.SimpleDialogListener, AdvanceSearchDialog.AdvanceDialogListener {
    private String searchURL;
    private String searchTitle;
    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intanciate database and get info
        db = new Database(this);
        UtilityClass.getInstance().setupBookmarkCtrl(this);
        getUserPreferences();

        //setup toolbar, page adapter and view pager
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                UtilityClass.getInstance().setActualPagePosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.advanceSearch:
                openAdvanceDialog();
                return true;
            case R.id.account:
                Intent intent = new Intent(this, AccountSetting.class);
                startActivity(intent);
                return true;
            case R.id.bookmarkList:
                Intent intentB = new Intent(this, BookmarkList.class);
                startActivity(intentB);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openAdvanceDialog(){
        AdvanceSearchDialog advanceSearchDialog = new AdvanceSearchDialog();
        advanceSearchDialog.show(getSupportFragmentManager(), "Advance search dialog");
    }

    public void openDialog(){

        SimpleSearchDialog simpleSearchDialog = new SimpleSearchDialog();
        simpleSearchDialog.show(getSupportFragmentManager(), "Simple search Dialog");
    }

    @Override
    public void makeSearch(String topic, Boolean newest, String lang) {
        if(topic.isEmpty()){
            toast("You must enter a topic");
            return;
        }

        searchTitle = topic.toUpperCase();
        String key = topic.replaceAll("\\s", "+");

        this.searchURL = getString(R.string.search_everywere) + key.toLowerCase() + getString(R.string.in_langauge) + lang +
                getString(R.string.searche_date);

        if(newest){
            this.searchURL += getString(R.string.by_newest);
        }else {
            this.searchURL +=  getString(R.string.by_popularity) ;
        }

        this.searchURL += getString(R.string.api_key);

        sectionsPagerAdapter.updateFragmentForSearch(searchURL, searchTitle);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void makeAdvanceSearch(String topic, Boolean bySource, String source, String country, String category) {

        if(bySource){
            searchURL = getString(R.string.headlines_by_src) + source;
            searchTitle = topic.isEmpty() ?  "Search" : topic;
        }else {
            searchURL = getString(R.string.headlines_by_country) + country;
            searchTitle = topic.isEmpty() ?  category : topic;
            searchTitle = searchTitle.equals("none") ? "Search" : searchTitle;
            if(!category.equals("none"))
                searchURL += getString(R.string.using_category) + category;
        }

        String key;

        if(!topic.isEmpty()) {
            key = topic.replaceAll("\\s", "+");
            searchURL += getString(R.string.headlines_with_key) + key.toLowerCase();
        }

        searchURL += getString(R.string.api_key);
        sectionsPagerAdapter.updateFragmentForSearch(searchURL, searchTitle);
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public void toast(String msg){
        Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
    }

    private void getUserPreferences() {
        SharedPreferences sharedPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        String tab1 = sharedPrefs.getString("tab1", "general");
        UtilityClass.getInstance().setTab1(tab1);
        String tab2 = sharedPrefs.getString("tab2", "technology");
        UtilityClass.getInstance().setTab2(tab2);
        String tab3 = sharedPrefs.getString("tab3", "science");
        UtilityClass.getInstance().setTab3(tab3);

    }

}