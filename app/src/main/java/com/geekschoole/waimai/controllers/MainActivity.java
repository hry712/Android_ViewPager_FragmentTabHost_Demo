package com.geekschoole.waimai.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.geekschoole.waimai.views.IndexFragment;
import com.geekschoole.waimai.views.OrderFragment;
import com.geekschoole.waimai.R;
import com.geekschoole.waimai.views.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, FragmentTabHost.OnTabChangeListener{

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentsArr[] = {IndexFragment.class, OrderFragment.class, UserFragment.class};
    private int ImageViewArr[] = {R.drawable.bottom_index_tab_selector,
            R.drawable.bottom_order_tab_selector,
            R.drawable.bottom_user_tab_selector};
    private String TabNameArr[] = {"Index", "Order", "User"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTabs();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);

        pager = (ViewPager) findViewById(R.id.pager_fragments);
        pager.addOnPageChangeListener(this);

        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost_pages);
        fragmentTabHost.setOnTabChangedListener(this);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.frame_tabContent);

        int count = fragmentsArr.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(TabNameArr[i]).setIndicator(getTabItemViewById(i));
            fragmentTabHost.addTab(tabSpec, fragmentsArr[i], null);
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bottom_switcher);
        }
    }

    private View getTabItemViewById(int index) {
        View view = layoutInflater.inflate(R.layout.bottom_tab_switcher, null);

        ImageView imageViewTabIcon = (ImageView) view.findViewById(R.id.imgvw_bottom_tabIcon);
        imageViewTabIcon.setImageResource(ImageViewArr[index]);

        TextView textViewTabName = (TextView) view.findViewById(R.id.tv_bottom_tabText);
        textViewTabName.setText(TabNameArr[index]);

        return view;

    }

    private void initTabs() {
        // 这里的添加顺序是否对 tab 页的前后顺序有影响
        fragmentList.add(new IndexFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new UserFragment());

        pager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),
                fragmentList));
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = fragmentTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        fragmentTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onTabChanged(String s) {
        pager.setCurrentItem(fragmentTabHost.getCurrentTab());
    }
}
