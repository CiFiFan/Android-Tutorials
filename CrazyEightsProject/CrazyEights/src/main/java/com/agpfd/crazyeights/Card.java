package com.agpfd.crazyeights;

import android.graphics.Bitmap;

/**
 * Created by sendo on 29/10/13.
 */
public class Card {

    private int id;
    private int suit;
    private int rank;
    private Bitmap bmp;

    public Card(int newId) {
        id = newId;
        suit = Math.round((id/100)*100);
        rank = id-suit;
    }

    public void setBitmap(Bitmap newBitmap) {
        bmp = newBitmap;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public int getId() {
        return id;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}
