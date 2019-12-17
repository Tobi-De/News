package com.example.news.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.news.R;
import com.example.news.UtilityClass;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private static final ArrayList<TabFragment> TAB_CONTENT = new ArrayList<>();
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        //this avoid the creation of tabs each time sectionPageAdapter is instanciate
        if(TAB_CONTENT.size() == 0){
            String tab1 = UtilityClass.getInstance().getTab1();
            String tab2 = UtilityClass.getInstance().getTab2();
            String tab3 = UtilityClass.getInstance().getTab3();

            //add title to TAB_TITLES
            TAB_TITLES.add(tab1.toUpperCase());
            TAB_TITLES.add(tab2.toUpperCase());
            TAB_TITLES.add(tab3.toUpperCase());

            //add fragment to TAB_CONTENT
            addFragment(getHeadlineUrl(tab1),0);
            addFragment(getHeadlineUrl(tab2),1);
            addFragment(getHeadlineUrl(tab3),2);
        }
    }

    @NonNull
    @Override
    public TabFragment getItem(int position) {
        return TAB_CONTENT.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return TAB_CONTENT.size();
    }

    public void addFragment(String url, int position){
        Bundle bundle = new Bundle();
        TabFragment fragment = new TabFragment();
        bundle.putString("url", url);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        TAB_CONTENT.add(fragment);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateFragmentForSearch(String url, String searchTitle){
        TAB_CONTENT.get(UtilityClass.getInstance().getActualPagePosition()).update(url);
        TAB_TITLES.set(UtilityClass.getInstance().getActualPagePosition(), searchTitle.toUpperCase());
    }

    public String getHeadlineUrl(String topic){
        return mContext.getResources().getString(R.string.headlines_by_country) + "us" + mContext.getResources().getString(R.string.using_category) + topic + mContext.getResources().getString(R.string.api_key);
    }
}