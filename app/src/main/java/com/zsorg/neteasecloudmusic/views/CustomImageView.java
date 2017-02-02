package com.zsorg.neteasecloudmusic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.zsorg.neteasecloudmusic.R;


/**
 * TODO: document your custom view class.
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    public static final int NORMAL = 0;
    public static final int ROUNDREC = 1;
    public static final int CIRCLE = 2;

    private int imageType = 0;

    private Paint mPaint;
    private Bitmap mSrc;

    private int mWidth, mHeight;
    private float mRadius;

    public CustomImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (null != getDrawable()) {
            mSrc = (((BitmapDrawable) getDrawable()).getBitmap());
        }
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (null != getDrawable()) {
            mSrc = (((BitmapDrawable) getDrawable()).getBitmap());
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            mWidth = size;
        } else {
            mWidth = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
            if (mode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(mWidth, size);
            }
        }

        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            mHeight = size;
        } else {
            mHeight = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
            if (mode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(mHeight, size);
            }
        }
        if (CIRCLE == imageType) {
            int max = Math.min(mWidth, mHeight);
            setMeasuredDimension(max, max);
        } else
            setMeasuredDimension(mWidth, mHeight);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomImageView, defStyle, 0);


        if (a.hasValue(R.styleable.CustomImageView_imageType)) {
            imageType = a.getInt(
                    R.styleable.CustomImageView_imageType, 0);
        }

        mRadius = a.getDimension(R.styleable.CustomImageView_roundRadius, 20);

        if (null != getDrawable()) {
            mSrc = (((BitmapDrawable) getDrawable()).getBitmap());
        }

        a.recycle();

        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(80);

        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth()  - paddingRight;
        int contentHeight = getHeight()  - paddingBottom;

        RectF ovalRect = new RectF(paddingLeft, paddingTop, contentWidth, contentHeight);

        if (null!=mSrc) {
            switch (imageType) {
                case NORMAL:
                    canvas.drawBitmap(mSrc, new Rect(0, 0, mSrc.getWidth(), mSrc.getHeight()), ovalRect, mPaint);
                    break;
                case ROUNDREC:
                    canvas.drawBitmap(transformBitmap(mSrc,ROUNDREC,mRadius), new Rect(0, 0, mSrc.getWidth(), mSrc.getHeight()), ovalRect, mPaint);
                    break;
                case CIRCLE:
                    canvas.drawBitmap(transformBitmap(mSrc,CIRCLE,0), new Rect(0, 0, mSrc.getWidth(), mSrc.getHeight()), ovalRect, mPaint);
                    break;
            }
        }

    }


    public Bitmap transformBitmap(Bitmap bitmap, int imageType, float mRadius) {

        Bitmap output;
        output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);


        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);


        paint.setAntiAlias(true);


        if (imageType == CIRCLE) {
            canvas.drawOval(rectF, paint);
        } else {
            canvas.drawRoundRect(rectF,mRadius,mRadius,paint);
        }

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }



}
