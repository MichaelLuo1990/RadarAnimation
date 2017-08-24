package com.michael.radar.circlewaveview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;

import com.michael.radar.utils.Utils;

/**
 * Created by michaelluo on 17/3/10.
 *
 * @desc 圆形波纹视图效果
 */
public class CircleWaveView extends View {
    private Bitmap bitmap;
    private Paint paint;
    private Canvas canvas;
    private int screenWidth;
    private int screenHeight;
    private boolean isSpreadFlag = false;//标记是否发射完成

    public boolean isSpreadFlag() {
        return isSpreadFlag;
    }

    public void setIsSpreadFlag(boolean isSpreadFlag) {
        this.isSpreadFlag = isSpreadFlag;
    }

    public CircleWaveView(Context context, int width, int height, int statusHeight) {
        super(context);
        screenWidth = Utils.getScreenWidth((Activity) context);
        screenHeight = Utils.getScreenHeight((Activity) context);
        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888); // 设置位图的宽高
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAlpha(100);
        paint.setShadowLayer(10, 0, 0, Color.LTGRAY);
        int[] mColors = new int[]{
                Color.TRANSPARENT, Color.LTGRAY
        };
        float[] mPositions = new float[]{
                0f, 0.1f
        };
        Shader shader = new RadialGradient(screenWidth / 2, screenHeight / 2, width / 2 + 10, mColors, mPositions,
                Shader.TileMode.MIRROR);
        paint.setShader(shader);
        //width / 2 + 10--》参数正好在环绕在头像周围
        canvas.drawCircle(screenWidth / 2, (screenHeight - statusHeight) / 2, width / 2 + 10, paint);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}
