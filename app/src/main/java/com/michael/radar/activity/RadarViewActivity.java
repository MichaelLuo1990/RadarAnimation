package com.michael.radar.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.michael.radar.R;
import com.michael.radar.circleimage.CircularImage;
import com.michael.radar.circlewaveview.CircleWaveView;

import java.util.ArrayList;
import java.util.List;

public class RadarViewActivity extends AppCompatActivity {

    private CircularImage mCircularImage;
    private CircleWaveView circleWaveView;
    private RelativeLayout relativeLayout;
    private List<CircleWaveView> circleWaveViewList;
    private int statusBarHeight;
    private Animator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);

        mCircularImage = (CircularImage) findViewById(R.id.ci_head);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_radar_border);
        circleWaveViewList = new ArrayList<>();
        mCircularImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleWaveView.setVisibility(View.GONE);//发射圆圈，即将循环动画View隐藏
                final CircleWaveView item = new CircleWaveView(RadarViewActivity.this, mCircularImage.getWidth(), mCircularImage.getHeight(), statusBarHeight);
                Animator spreadAnim = AnimatorInflater.loadAnimator(RadarViewActivity.this, R.anim.circle_spread_animator);
                spreadAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        item.setIsSpreadFlag(true);//动画执行完成，标记一下
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                spreadAnim.setTarget(item);
                spreadAnim.start();
                circleWaveViewList.add(item);
                relativeLayout.addView(item);
                relativeLayout.invalidate();
                Message message = handler.obtainMessage(1);
                handler.sendMessageDelayed(message, 10); //发送message,定时释放LYJCircleView
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < circleWaveViewList.size(); i++) {
                        if (circleWaveViewList.get(i).isSpreadFlag()) {
                            relativeLayout.removeView(circleWaveViewList.get(i));
                            circleWaveViewList.remove(i);
                            relativeLayout.invalidate();
                        }
                    }
                    if (circleWaveViewList.size() <= 0) {
                        circleWaveView.setVisibility(View.VISIBLE);
                    }
                    Message message = handler.obtainMessage(1);
                    handler.sendMessageDelayed(message, 10);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        mCircularImage.post(new Runnable() {
            @Override
            public void run() {
                circleWaveView = new CircleWaveView(RadarViewActivity.this, mCircularImage.getWidth(), mCircularImage.getHeight(), statusBarHeight);
                relativeLayout.addView(circleWaveView);
                relativeLayout.postInvalidate();
                // 加载动画
                anim = AnimatorInflater.loadAnimator(RadarViewActivity.this, R.anim.circle_scale_animator);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        anim.start();//循环执行动画
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anim.setTarget(circleWaveView);
                anim.start();
            }
        });
    }

}