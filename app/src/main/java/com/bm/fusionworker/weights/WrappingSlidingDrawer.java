package com.bm.fusionworker.weights;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SlidingDrawer;

import com.bm.fusionworker.R;

/**
 * Created by issuser on 2018/5/10.
 */

public class WrappingSlidingDrawer extends SlidingDrawer {

    public WrappingSlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int orientation = attrs.getAttributeIntValue("android", "orientation", ORIENTATION_VERTICAL);
        mTopOffset = attrs.getAttributeIntValue("android", "topOffset", 0);
        mVertical = (orientation == SlidingDrawer.ORIENTATION_VERTICAL);
    }

    public WrappingSlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);

        int orientation = attrs.getAttributeIntValue("android", "orientation", ORIENTATION_VERTICAL);
        mTopOffset = attrs.getAttributeIntValue("android", "topOffset", 0);
        mVertical = (orientation == SlidingDrawer.ORIENTATION_VERTICAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.UNSPECIFIED || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            throw new RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
        }

        final View handle = getHandle();
        final View content = getContent();
        measureChild(handle, widthMeasureSpec, heightMeasureSpec);

        if (mVertical) {
            int height = heightSpecSize - handle.getMeasuredHeight() - mTopOffset;
            content.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, heightSpecMode));
            heightSpecSize = handle.getMeasuredHeight() + mTopOffset + content.getMeasuredHeight();
            widthSpecSize = content.getMeasuredWidth();
            if (handle.getMeasuredWidth() > widthSpecSize)
                widthSpecSize = handle.getMeasuredWidth();
        } else {
            int width = widthSpecSize - handle.getMeasuredWidth() - mTopOffset;
            getContent().measure(MeasureSpec.makeMeasureSpec(width, widthSpecMode), heightMeasureSpec);
            widthSpecSize = handle.getMeasuredWidth() + mTopOffset + content.getMeasuredWidth();
            heightSpecSize = content.getMeasuredHeight();
            if (handle.getMeasuredHeight() > heightSpecSize)
                heightSpecSize = handle.getMeasuredHeight();
        }

        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }


    private boolean mVertical;
    private int mTopOffset;

    //把手内部点击响应
    private ViewGroup mHandleLayout;
    private final Rect mHitRect = new Rect();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View handle = getHandle();

        if (handle instanceof ViewGroup) {
            mHandleLayout = (ViewGroup) handle;
        }
    }

    private float DownX;
    private float DownY;
    private float moveX;
    private float moveY;
    private long currentMS;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mHandleLayout != null) {
//            mHandleLayout.setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN:
//                            DownX = event.getX();//float DownX
//                            DownY = event.getY();//float DownY
//                            moveX = 0;
//                            moveY = 0;
//                            currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            moveX += Math.abs(event.getX() - DownX);//X轴距离
//                            moveY += Math.abs(event.getY() - DownY);//y轴距离
//                            DownX = event.getX();
//                            DownY = event.getY();
//                            Log.e("dp", "----moveY=" + moveY);
//                            break;
//                        case MotionEvent.ACTION_UP:
//                            long moveTime = System.currentTimeMillis() - currentMS;//移动时间
//                            //判断是否继续传递信号
//                            if (moveTime > 200 && (moveX > 20 || moveY > 20)) {
//                                return true; //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
//                            }
//                            break;
//                    }
//                    return false;
//                }
//            });
            int childCount = mHandleLayout.getChildCount();
            int handleClickX = (int) (event.getX() - mHandleLayout.getX());
            int handleClickY = (int) (event.getY() - mHandleLayout.getY());

            Rect hitRect = mHitRect;

            for (int i = 0; i < childCount; i++) {
                View childView = mHandleLayout.getChildAt(i);
                childView.getHitRect(hitRect);
                if (hitRect.contains(handleClickX, handleClickY)) {
                    return false;
                }
            }
        }
        return super.onInterceptTouchEvent(event);
    }
}
