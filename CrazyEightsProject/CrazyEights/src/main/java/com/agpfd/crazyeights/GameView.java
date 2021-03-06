package com.agpfd.crazyeights;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by sendo on 29/10/13.
 */
public class GameView extends View {

    private int screenW;
    private int screenH;
    private Context myContext;
    private List<Card> deck = new ArrayList<Card>();
    private int scaledCardW;
    private int scaledCardH;
    private List<Card> myHand = new ArrayList<Card>();
    private List<Card> oppHand = new ArrayList<Card>();
    private List<Card> discardPile = new ArrayList<Card>();
    private float scale;
    private Paint whitePaint;
    private int oppScore;
    private int myScore;
    private Bitmap cardBack;
    private boolean myTurn;
    private int movingCardIdx = -1;
    private int movingX;
    private int movingY;

    public GameView (Context context) {
        super(context);
        myContext = context;
        scale = myContext.getResources().getDisplayMetrics().density;
        //myTurn = new Random().nextBoolean();
        myTurn = true;
        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setTextAlign(Paint.Align.LEFT);
        whitePaint.setTextSize(scale*15);
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
        scaledCardW = (int) (screenW/8);
        scaledCardH = (int) (scaledCardW*1.28);
        cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

        initCards();
        dealCards();
        drawCard(discardPile);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("Computer Score: " + Integer.toString(oppScore), 10, whitePaint.getTextSize()+10, whitePaint);
        canvas.drawText("My Score: " + Integer.toString(myScore), 10, screenH-whitePaint.getTextSize()-10, whitePaint);

        for (int i = 0; i < oppHand.size(); i++) {
            canvas.drawBitmap(cardBack, i*(scale*5), whitePaint.getTextSize()+(50*scale), null);
        }

        canvas.drawBitmap(cardBack, (screenW/2)-cardBack.getWidth()-10, (screenH/2)-(cardBack.getHeight()/2), null);

        if (!discardPile.isEmpty()) {
            canvas.drawBitmap(discardPile.get(0).getBitmap(), (screenW/2)+10, (screenH/2)-(cardBack.getHeight()/2), null);
        }

        for (int i = 0; i < myHand.size(); i++) {
            if (i == movingCardIdx) {
                canvas.drawBitmap(myHand.get(i).getBitmap(), movingX, movingY, null);
            } else {
                canvas.drawBitmap(myHand.get(i).getBitmap(), i*(scaledCardW+5), screenH-scaledCardH-whitePaint.getTextSize()-(50*scale), null);
            }
        }
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventaction ) {

            case MotionEvent.ACTION_DOWN:
                if (myTurn) {
                    for (int i = 0; i < 7; i++) {
                        if (X > i*(scaledCardW+5) &&
                                X < i*(scaledCardW+5)+scaledCardW &&
                                Y > screenH-scaledCardH-whitePaint.getTextSize()-(50*scale)) {
                            movingCardIdx = i;
                            movingX = X-(int)(30*scale);
                            movingY = Y-(int)(70*scale);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                movingX = X-(int)(30*scale);
                movingY = Y-(int)(70*scale);
                break;

            case MotionEvent.ACTION_UP:
                movingCardIdx = -1;
                break;
        }
        invalidate();
        return true;
    }

    private void initCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 102; j < 115; j++) {
                int tempId = j + (i*100);
                Card tempCard = new Card(tempId);
                int resourceId = getResources().getIdentifier("card"+ tempId, "drawable", myContext.getPackageName());
                Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
                scaledCardW = (int) (screenW/8);
                scaledCardH = (int) (scaledCardW*1.28);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
                tempCard.setBitmap(scaledBitmap);
                deck.add(tempCard);
            }
        }
    }

    private void drawCard(List<Card> handToDraw) {
        handToDraw.add(0, deck.get(0));
        deck.remove(0);
        if (deck.isEmpty()) {
            for (int i = discardPile.size()-1; i > 0; i--) {
                deck.add(discardPile.get(i));
                discardPile.remove(i);
                Collections.shuffle(deck, new Random());
            }
        }
    }

    private void dealCards() {
        Collections.shuffle(deck, new Random());
        for (int i = 0; i < 7; i++) {
            drawCard(myHand);
            drawCard(oppHand);
        }
    }

}
