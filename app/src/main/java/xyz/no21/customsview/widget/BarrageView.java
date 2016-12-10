package xyz.no21.customsview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Keep;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xyz.no21.customsview.R;
import xyz.no21.customsview.models.BarrageMessageModel;


/**
 * Created by lin on 2016/12/5.
 * Email:L437943145@gmail.com
 * <p>
 * desc: 显示弹幕的view
 */

public class BarrageView extends RelativeLayout implements ValueAnimator.AnimatorUpdateListener, Runnable {


    public static final String TAG = "BarrageView";
    boolean enable = true;
    private Paint paint;
    private float viewWidth;
    private float viewHeight;
    private Random random;
    private List<BarrageContentView> barrageContentViews;
    private Rect rect;
    private long filter;
    private RectF rectF;

    public BarrageView(Context context) {
        super(context);
        init();
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barrageContentViews = new ArrayList<>(50);
        rect = new Rect();
        rectF = new RectF();setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h - 60;//避免显示在屏幕外
        random = new Random();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (BarrageContentView item : barrageContentViews) {
            item.draw(canvas, paint);
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(this, 200);//测试用

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this);
    }

    @Override
    public void run() {
        BarrageMessageModel messageModel = new BarrageMessageModel("测试消息");

        handleBarrageMessage(messageModel);
        postDelayed(this, 200);
    }

    public void handleBarrageMessage(BarrageMessageModel model) {
        if (model.getType() == BarrageMessageModel.TYPE_TEXT && enable) {
            if (System.currentTimeMillis() - filter > 30) {
                filter = System.currentTimeMillis();
            } else return;

            final BarrageContentView contentView = new BarrageContentView(viewWidth, random.nextInt((int) viewHeight) + 60, model.getContent());
            barrageContentViews.add(contentView);
            PropertyValuesHolder xPosition = PropertyValuesHolder.ofFloat("xPosition", -paint.measureText(model.getContent()));
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(contentView, xPosition);
            animator.setDuration(5000);
            animator.addUpdateListener(this);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    barrageContentViews.remove(contentView);
                }
            });
            animator.start();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    private class BarrageContentView {
        float XPosition;
        float YPosition;
        String text;

        BarrageContentView(float xPosition, float yPosition, String text) {
            this.XPosition = xPosition;
            this.YPosition = yPosition;
            this.text = text;
        }

        void draw(Canvas canvas, Paint paint) {
            if (!TextUtils.isEmpty(text)) {

                paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.standard_text_title_size_h3));
                paint.setTypeface(Typeface.DEFAULT_BOLD);

                paint.setStyle(Paint.Style.FILL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.black_20));
                paint.getTextBounds(text, 0, text.length(), rect);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();

                rectF.left = XPosition - 5;
                rectF.top = YPosition + fontMetrics.ascent;
                rectF.right = XPosition + rect.width() + 5;
                rectF.bottom = YPosition + fontMetrics.descent;
                canvas.drawRoundRect(rectF, 6, 6, paint);

                Log.e(TAG, fontMetrics.toString());
                paint.setColor(ContextCompat.getColor(getContext(), R.color.white));
                paint.setStrokeWidth(0);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText(text, XPosition, YPosition, paint);

                paint.setColor(ContextCompat.getColor(getContext(), R.color.standard_color_h1_dark));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1.8f);
                canvas.drawText(text, XPosition, YPosition, paint);
            }

        }

        @Keep
        public float getXPosition() {
            return XPosition;
        }

        @Keep
        public void setXPosition(float XPosition) {
            this.XPosition = XPosition;
        }

        @Keep
        public float getYPosition() {
            return YPosition;
        }

        @Keep
        public void setYPosition(float YPosition) {
            this.YPosition = YPosition;
        }
    }
}
