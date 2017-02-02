package com.zsorg.neteasecloudmusic.widgets;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zsorg.neteasecloudmusic.LineItemDecorator;
import com.zsorg.neteasecloudmusic.R;
import com.zsorg.neteasecloudmusic.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project:NeteaseCloudMusic
 *
 * @Author: piyel_000
 * Created on 2017/1/27.
 * E-mail:piyell@qq.com
 */

public class BaseBottomSheetDialog extends BottomSheetDialog {

    @BindView(R.id.layout_top)
    ViewGroup topLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private BottomSheetBehavior<View> mBehavior;
    private View mParent;

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        init();
    }

    public BaseBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public BaseBottomSheetDialog setListAdapter(RecyclerView.Adapter adapter) {
        rvList.setAdapter(adapter);
        return this;
    }

    public BaseBottomSheetDialog setHeaderView(View view) {
        topLayout.addView(view);
        return this;
    }

    public View setHeaderView(int resID) {
        return View.inflate(getContext(), resID, topLayout);
    }

    public void setPeekHeight(Activity activity) {
        int measuredHeight = mParent.getMeasuredHeight();
        int height = ScreenUtil.getScreenHeight(activity) - ScreenUtil.getStatusBarHeight(getContext());

        measuredHeight = measuredHeight > height * 0.5 ? (int) (height * 0.5) : measuredHeight;

        mBehavior.setPeekHeight(measuredHeight);
        mBehavior.setHideable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent.measure(0, 0);
    }

    private void init() {

        View view = View.inflate(getContext(), R.layout.bottom_sheet_playlist, null);
        setContentView(view);

        mParent = (View) view.getParent();
        mBehavior = BottomSheetBehavior.from(mParent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        ButterKnife.bind(this,view);

        LineItemDecorator decorator = LineItemDecorator.getInstance();
        rvList.addItemDecoration(decorator);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));


    }

}
