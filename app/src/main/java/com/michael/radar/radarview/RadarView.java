package com.michael.radar.radarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.michael.radar.R;

/**
 * Created by michaelluo on 17/3/10.
 *
 * @desc 雷达扫描显示效果
 */
@SuppressLint("DrawAllocation")
public class RadarView extends ImageView {
	private int w, h;// 获取控件宽高
	private Matrix matrix;
	private int degrees;
	private Handler mHandler = new Handler();
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			degrees++;
			matrix.postRotate(degrees, w / 2, h / 2);
			RadarView.this.invalidate();// 重绘
			mHandler.postDelayed(mRunnable, 50);
		}
	};

	public RadarView(Context context) {
		this(context, null);
	}

	public RadarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		w = getMeasuredWidth();//获取view的宽度
		h = getMeasuredHeight();//获取view的高度
	}

	/**
	 * 初始化
	 */
	private void init() {
		setBackgroundResource(R.drawable.radar_bg);
		matrix = new Matrix();
		mHandler.postDelayed(mRunnable,500);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setMatrix(matrix);
		super.onDraw(canvas);
		matrix.reset();
	}

}
