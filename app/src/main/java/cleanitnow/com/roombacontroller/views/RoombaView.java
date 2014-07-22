package cleanitnow.com.roombacontroller.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import cleanitnow.com.roombacontroller.Consts;

/**
 * Created by madhur on 19-Jul-14.
 */
public class RoombaView extends View
{

    private int width;
    private int height;


    public RoombaView(Context context)
    {
        super(context);


    }

    public RoombaView(Context context, AttributeSet attrs)
    {
        super(context, attrs);


    }

    public RoombaView(Context context, AttributeSet attrs, int defStyleattrs)
    {
        super(context, attrs, defStyleattrs);


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = 500;
        int desiredHeight = 500;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);



        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY)
        {
            //Must be this size
            width = widthSize;
        }
        else if (widthMode == MeasureSpec.AT_MOST)
        {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        }
        else
        {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY)
        {
            //Must be this size
            height = heightSize;
        }
        else if (heightMode == MeasureSpec.AT_MOST)
        {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        }
        else
        {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

        if(w<h)
        {
            this.height=this.width=w;
        }
        else
        {
            this.height=this.width=h;
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {



        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect r;

        for (int rows = 0; rows < Consts.NUM_ROWS; ++rows)
        {
            for (int cols = 0; cols < Consts.NUM_COLS; ++cols)
            {

                r = new Rect(cols *  getSquareWidth(), rows*getSquareHeight(), getSquareWidth() + getSquareWidth() * cols, getSquareHeight()+ rows*getSquareHeight());


                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLACK);

                canvas.drawRect(r, paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth((float) 0.5);
                paint.setColor(Color.DKGRAY);
                canvas.drawRect(r, paint);
            }
        }


    }

    public int getTotalWidth()
    {
        return width;
    }

    public int getTotalHeight()
    {
        return height;
    }

    public int getSquareWidth()
    {
        return  width / Consts.NUM_COLS;
    }


    public int getSquareHeight()
    {
        return height/Consts.NUM_ROWS;
    }


}
