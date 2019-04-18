package com.example.guardianangelsafetyapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class TempRing extends View {

    Paint p;
    int color ;
    public TempRing(Context context) {
        this(context, null);
    }

    public TempRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TempRing(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // real work here
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TempRing,
                0, 0
        );

        try {

            color = a.getColor(R.styleable.TempRing_circleColor, 0xff000000);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        init();
    }

    public void setColor(int newcolor)
    {
        color = newcolor;
    }

    public void init()
    {
        p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(50F);
        p.setAntiAlias(true);
        p.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if(canvas!=null)
        {
            canvas.drawCircle(getHeight()/2, getWidth()/2,getWidth()/2.5f, p );
        }
    }

}
