package com.example.andy.sudokudefinitivoapp;

import android.graphics.Bitmap;


import com.example.andy.sudokudefinitivoapp.TouchHandler.TouchEvent;

import java.util.List;

/**
 * Created by jvilar on 29/03/16.
 */
public interface IGameController {
    void onUpdate(float deltaTime, List<TouchEvent> touchEvents);
    Bitmap onDrawingRequested();
}
