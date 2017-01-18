package com.zsorg.neteasecloudmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.zsorg.neteasecloudmusic.presenters.SearchAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchView.OnCloseListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.radio)
    RadioGroup mRadioGroup;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.bottomLayout)
    View mBottomLayout;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRvSearch.addItemDecoration(LineItemDecorator.getInstance());
        mRvSearch.setAdapter(new SearchAdapter(getLayoutInflater()));

        mNavigationView.setNavigationItemSelectedListener(this);
        mSearchView.setOnSearchClickListener(this);
        mSearchView.setOnCloseListener(this);

        adapter = new MainAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);
        mViewpager.addOnPageChangeListener(this);

        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            startActivity(new Intent(this, ScanMusicActivity.class));
        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * SearchView单击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        mRadioGroup.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
        mRvSearch.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bottomLayout)
    public void onPlayClick(){
        startActivity(new Intent(this,PlayerActivity.class));
    }

    /**
     * SearchView关闭事件
     *
     */
    @Override
    public boolean onClose() {
        mRadioGroup.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.VISIBLE);
        mRvSearch.setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRadioGroup.check(position == 0 ? R.id.action_music : R.id.action_list);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        mViewpager.setCurrentItem(i == R.id.action_music ? 0 : 1);
    }
}
