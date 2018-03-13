package com.moshesteinvortzel.assaftayouri.ihw.GUI.Views;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by assaftayouri on 05/03/2018.
 */

public class Oval extends android.graphics.drawable.Drawable
{
    private final String WHITE = "#ffffff";
    private final int WIDTH = 5;
    private final int RADIUS = 110;

    private android.graphics.Paint paint;
    private android.graphics.Rect bounds_rect;
    private int color;

    public Oval(int color)
    {
        this.color = color;
        this.paint = new android.graphics.Paint();
    }

    @Override
    public void onBoundsChange(android.graphics.Rect bounds)
    {
        this.bounds_rect = bounds;
    }

    public void draw(android.graphics.Canvas c)
    {
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(WIDTH);
        this.paint.setColor(color);
        c.drawCircle(this.bounds_rect.centerX(), this.bounds_rect.centerY(), RADIUS, this.paint);
    }

    public void setAlpha(int a)
    {
        // TODO: Implement this method
    }

    public void setColorFilter(android.graphics.ColorFilter cf)
    {
        // TODO: Implement this method
    }

    public int getOpacity()
    {
        // TODO: Implement this method
        return 0;
    }
}
