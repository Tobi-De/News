package com.example.news.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Article;
import com.example.news.ArticleContent;
import com.example.news.ArticleListCache;
import com.example.news.HttpHandler;
import com.example.news.R;
import com.example.news.UtilityClass;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private LinearLayout progressLayout;
    private ArrayList<Article> articleList = new ArrayList<>();
    private static ArrayList<ArticleListCache> articlesListCache = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private String URL;
    private View root;
    private int position2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URL = getArguments() != null ? getArguments().getString("url") : "";
        position2 = getArguments() != null ? getArguments().getInt("position") : 5;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new RecyclerViewAdapter(articleList);
        RecyclerView myView =  root.findViewById(R.id.recycler);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        progressLayout = root.findViewById(R.id.progressLayout);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                final Article choosenArticle = articleList.get(position);
                Intent intent = new Intent(root.getContext() , ArticleContent.class);
                bundle.putString("url", choosenArticle.getUrl());
                bundle.putString("title", choosenArticle.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        swipeContainer = root.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (position2){
                    case 0:
                        URL = getHeadlineUrl(UtilityClass.getInstance().getTab1());
                        break;
                    case 1:
                        URL = getHeadlineUrl(UtilityClass.getInstance().getTab2());
                        break;
                    case 2:
                        URL = getHeadlineUrl(UtilityClass.getInstance().getTab3());
                        break;
                    default:
                        break;
                }
                new FetchDataTask().execute();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if(!getArticlesFromCache(URL))
            new FetchDataTask().execute();
        return root;
    }

    private  class FetchDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //toast("JSON data is downloading");
            progressLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            //making request and getting response
            String jsonStr = sh.makeServiceCall(URL);

            //clear list to avoir repetition
            articleList.clear();

            if(jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray articles = jsonObj.getJSONArray("articles");

                    //this is just to avoid getting 2000 articles on the page
                    int nbrArticles;
                    nbrArticles =  articles.length() > 20 ? 30 : articles.length();

                    //looping through data
                    for(int i= 0; i <nbrArticles; i++){
                        JSONObject a = articles.getJSONObject(i);
                        if(a != null){
                            String title = a.getString("title");
                            String author = a.getString("author");
                            String description = a.getString("description");
                            String url = a.getString("url");
                            String urlToImage = a.getString("urlToImage");
                            String publishedAt = a.getString("publishedAt");

                            //node object
                            JSONObject sourceG = a.getJSONObject("source");
                            String source = sourceG.getString("name");

                            //add data to array
                            articleList.add(new Article(source, title, author, description, url, urlToImage, publishedAt));
                        }
                    }
                    articlesListCache.add(new ArticleListCache(articleList ,URL));
                }catch (final JSONException e){
                    Log.e("APP", "json parsing error: " + e.getMessage());
                }
            }else {
                Log.e("App", "couldn't get json from server");
                Snackbar.make(root, "Couldn't fetch news, maybe connection ? :( ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(articleList.size() == 0)
                UtilityClass.toast(getContext(),"Sorry Nothing found :( ");
            adapter.notifyDataSetChanged();
            progressLayout.setVisibility(View.GONE);
        }
    }

    void update(String url){
        this.URL = url;
        new FetchDataTask().execute();
    }

    private boolean getArticlesFromCache(String URL){
        for(int i=0; i<articlesListCache.size(); i++){
            if(URL.equals(articlesListCache.get(i).getURL())){
              articleList = articlesListCache.get(i).getArticlesList();
              return true;
            }
        }
        return false;
    }

    private String getHeadlineUrl(String topic){
        return getContext().getResources().getString(R.string.headlines_by_country) + "us" + getContext().getResources().getString(R.string.using_category) + topic + getContext().getResources().getString(R.string.api_key);
    }
}