package com.agpfd.crazyeights;
/**
 * Created by sendo on 29/10/13.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;


public class TitleView extends View {

    private Bitmap titleGraphic;
    private Bitmap playButtonUp;
    private int screenW;
    private int screenH;

    public TitleView(Context context) {
        super(context);
        titleGraphic =
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.title_graphic);
        playButtonUp =
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.play_button_up);
    }

    @Override
    public  void  onSizeChanged (int w, int h, int oldw,
                                 int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
    }

    @Override
    protected  void  onDraw(Canvas canvas) {
        canvas.drawBitmap(titleGraphic,
                (screenW=titleGraphic.getWidth())/2, 0, null);
        canvas.drawBitmap(playButtonUp,
                (int)(screenW*0.5),
                (int)(screenH*0.7), null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
