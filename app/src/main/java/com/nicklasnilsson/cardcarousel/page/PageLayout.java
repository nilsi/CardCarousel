package com.nicklasnilsson.cardcarousel.page;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Nicklas Nilsson on 18/01/2016.
 */
public class PageLayout extends RelativeLayout {

    private float skew = 0;
    private float translate = 0;

    private Camera camera;

    public PageLayout(Context context) {
        super(context);
        camera = new Camera();
    }

    public PageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        camera = new Camera();
    }

    public PageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        camera = new Camera();
    }

    public void setSkew(float skew) {
        this.skew = skew;
        invalidate();
    }

    public void setTranslated(float translate) {
        this.translate = translate;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = canvas.getMatrix();

        camera.save();
        camera.rotateX(-skew);
        camera.translate(0, -translate, 0);
        camera.getMatrix(matrix);

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        matrix.preTranslate(-centerX, -centerY); //For getting the correct viewing perspective
        matrix.postTranslate(centerX, centerY);

        camera.restore();
        canvas.concat(matrix);
    }

}