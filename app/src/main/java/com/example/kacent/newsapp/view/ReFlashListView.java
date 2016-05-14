package com.example.kacent.newsapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.kacent.newsapp.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kacent on 2016/5/4.
 */
public class ReFlashListView extends ListView implements AbsListView.OnScrollListener {
    View header;
    int headerHeight;

    int firstVisibleItem;
    int visibleItemCount;
    int totalItemCount;

    boolean isLoding;//底部是否正在读取
    boolean isReMark;//头部是否正在读取
    int startY;
    int scrollState;
    public ReFlashListener reFlashListener;
    /*
    * 状态 定义
    * */
    int state;
    final int NONE = 0;
    final int PULL = 1;
    final int RELSE = 2;
    final int REFLASHING = 3;

    public ReFlashListView(Context context) {
        super(context);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /*
    * 初始化 顶部
    * */
    public void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);


        header = inflater.inflate(R.layout.header_flash, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();

        Log.i("tag", "容器高度是" + headerHeight + "");

        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);

    }

    public void measureView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int tempHeight;
        int height;
        tempHeight = layoutParams.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        view.measure(width, height);


    }

    /*设置上布局边距
    * */
    public void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        int tempTotal = firstVisibleItem + visibleItemCount;
        if (tempTotal == totalItemCount) {
            Log.i("tag", "已经到达底部");
            if (!isLoding) {

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isReMark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELSE) {
                    state = REFLASHING;
                    reFlashView();
                    reFlashListener.onReFlash();
                } else if (state == PULL) {
                    state = NONE;
                    isReMark = false;
                    reFlashView();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void onMove(MotionEvent event) {
        if (!isReMark) {
            return;
        }
        int tempY = (int) event.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reFlashView();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELSE;
                    reFlashView();
                }
                break;
            case RELSE:
                topPadding(topPadding);
                if (space < headerHeight) {
                    state = PULL;
                    reFlashView();
                } else if (space <= 0) {
                    state = NONE;
                    reFlashView();
                }
                break;

        }

    }


    /*
    * 定义刷新控件的方法
    * */
    public void reFlashView() {
        //绑定控件
        TextView tip = (TextView) header.findViewById(R.id.header_content);
        ImageView arow = (ImageView) header.findViewById(R.id.header_image);
        ProgressBar progressBar = (ProgressBar) header.findViewById(R.id.header_progressbar);
        //定义动画
        RotateAnimation rotateUp = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateUp.setDuration(500);
        rotateUp.setFillAfter(true);
        RotateAnimation rotateDown = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateDown.setDuration(500);
        rotateDown.setFillAfter(true);

        switch (state) {
            case NONE:
                arow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                arow.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                tip.setText("下拉刷新");
                arow.clearAnimation();
                arow.startAnimation(rotateDown);
                break;
            case RELSE:
                arow.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                tip.setText("松开可以刷新");
                arow.clearAnimation();
                arow.startAnimation(rotateUp);
                break;
            case REFLASHING:
                topPadding(35);
                arow.setVisibility(GONE);
                progressBar.setVisibility(VISIBLE);
                tip.setText("正在刷新");
                arow.clearAnimation();
                break;

        }
    }

    /**
     * 获取完数据；
     */
    public void reflashComplete() {
        state = NONE;
        isReMark = false;
        reFlashView();
        TextView lastupdatetime = (TextView) header
                .findViewById(R.id.header_updatedate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastupdatetime.setText(time);
    }

    public void setReFlashInterface(ReFlashListener reFlashListener) {
        this.reFlashListener = reFlashListener;
    }


    public interface ReFlashListener {
        public void onReFlash();
    }
}
