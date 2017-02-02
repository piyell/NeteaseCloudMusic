package com.zsorg.neteasecloudmusic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by piyel_000 on 2016/3/24.
 */
public class LineItemDecorator extends RecyclerView.ItemDecoration {

    private static LineItemDecorator lineItemDecorator;
    private int dimension;
    private boolean isDrawHorizontal=false;
    private boolean isDrawVertical=true;

    public LineItemDecorator() {
        super();
    }

    public void recycle() {
        lineItemDecorator = null;
    }

    public void setDrawHorizontal(boolean isDraw) {
        isDrawHorizontal = isDraw;
    }

    public void setDrawVertical(boolean isDraw) {
        isDrawVertical = isDraw;
    }

    public static LineItemDecorator getInstance() {
        if (null == lineItemDecorator) {
            synchronized (LineItemDecorator.class) {
                lineItemDecorator = new LineItemDecorator();
            }
        }
        return lineItemDecorator;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (isDrawVertical) {
            outRect.top = 1;
//        outRect.left = dimension;
//        outRect.right = dimension;
            outRect.bottom = 1;
        }
        if (isDrawHorizontal){
            outRect.left = 1;
//        outRect.left = dimension;
//        outRect.right = dimension;
            outRect.right = 1;
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (isDrawHorizontal) {
            drawHrozontal(c, parent);
        }
        if (isDrawVertical){
            drawVertical(c, parent);
        }
    }



    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        if (childCount<1)return;
//        int left = parent.getPaddingLeft()+leftView.getLeft()+leftView.getPaddingLeft();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getBottom()+params.bottomMargin;
            int bottom = top + 1;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawHrozontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        if (childCount<1)return;
//        int left = parent.getPaddingLeft()+leftView.getLeft()+leftView.getPaddingLeft();
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.LTGRAY);
        for (int i = 0; i < childCount-1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left = child.getRight()+params.rightMargin;
            int right = left + 1;
            c.drawRect(left, top, right, bottom, paint);

        }
    }

}
