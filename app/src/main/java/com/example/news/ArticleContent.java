package com.example.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleContent extends AppCompatActivity {

    LinearLayout progressLayout;
    private String url;
    private String title;
    WebView myWebView;
    //private BookmarksMethods bookmarksMethods;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("url");
            title = extras.getString("title");
            //The key argument here must match that used in the other activity
        }

        progressLayout = findViewById(R.id.progressLayout2);

        myWebView = findViewById(R.id.webview);
        myWebView.setWebViewClient(new myWebClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressLayout.setVisibility(View.GONE);
        }
    }

    // To handle &quot;Back&quot; key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.add_bookmark:
                int idDb = UtilityClass.getInstance().getBookmarkCtrl().getLastId() + 1;
                UtilityClass.getInstance().getBookmarkList().add(new BArticle(idDb, url, title));
                UtilityClass.getInstance().getBookmarkCtrl().insert(new BArticle(url, title));
                UtilityClass.toast(getApplicationContext(),"Add to Bookmark list");
                return true;
            case R.id.share_link:
                shareIt();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
