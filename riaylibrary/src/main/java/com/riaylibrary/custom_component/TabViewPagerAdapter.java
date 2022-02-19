package com.riaylibrary.custom_component;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();
    private final List<Long> mTitleCatId = new ArrayList();
    Context context;

    public TabViewPagerAdapter(FragmentManager manager, Context ctx) {
        super(manager);
        this.context = ctx;
    }

    public List<Long> getmTitleCatId() {
        return this.mTitleCatId;
    }

    public Fragment getItem(int position) {
        return (Fragment)this.mFragmentList.get(position);
    }

    public int getCount() {
        return this.mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }

    public void addFrag(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add("");
    }

    public void addFrag(Fragment fragment, Bundle bundle, String title) {
        fragment.setArguments(bundle);
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }

    public void addFrag(Fragment fragment, Bundle bundle, String title, long catId) {
        fragment.setArguments(bundle);
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
        this.mTitleCatId.add(catId);
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence)this.mFragmentTitleList.get(position);
    }
}