package com.moshesteinvortzel.assaftayouri.ihw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.webkit.WebHistoryItem;

/**
 * Created by assaftayouri on 05/03/2018.
 */

public class Border extends android.graphics.drawable.Drawable
{
    private final String WHITE="#ffffff";
    private android.graphics.Paint paint;
    private android.graphics.Rect bounds_rect;
    private String color;

    public Border(String color, int width)
    {
        this.color=color;
        this.paint = new android.graphics.Paint();
        this.paint.setStrokeWidth(width);
    }

    @Override
    public void onBoundsChange(android.graphics.Rect bounds)
    {
        this.bounds_rect = bounds;
    }

    public void draw(android.graphics.Canvas c)
    {
        paint.setColor(Color.parseColor(WHITE));
        this.paint.setStyle(Paint.Style.FILL);
        c.drawRect(this.bounds_rect, this.paint);

        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(Color.parseColor("#"+this.color));
        c.drawRect(this.bounds_rect, this.paint);
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
