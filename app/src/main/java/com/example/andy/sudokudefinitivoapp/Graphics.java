package com.example.andy.sudokudefinitivoapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by jvilar on 29/03/16.
 */
public class Graphics {
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;

    public Graphics(int width, int height) {
        this.frameBuffer = Bitmap.createBitmap(width, height, ARGB_8888);
        canvas = new Canvas(frameBuffer);
        paint = new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(20);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public Bitmap getFrameBuffer() {
        return frameBuffer;
    }

    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    public void drawLine(float startX, float startY, float stopX, float stopY, int color) {
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawBitmap(Bitmap bitmap, float x, float y){
        canvas.drawBitmap(bitmap, x, y, null);

    }

    public void drawText(String text, int x, int y, int size, int color){

        canvas.drawText(text, x, y, paint);
        paint.setTextSize(size);
        paint.setColor(color);
    }


    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }

}
