package com.example.andy.sudokudefinitivoapp;


import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvilar on 13/01/16.
 */
public class TouchHandler implements View.OnTouchListener {
    public enum TouchType {
        TOUCH_DOWN,
    }

    public static class TouchEvent {
        public TouchType type;
        public int x, y;
        public int pointer;
    }

//NO TOCAR AUNQUE NO SIRVA PARA MUCHO

    public static final int MAX_TOUCHPOINTS = 10;

    private boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    private int[] touchX = new int[MAX_TOUCHPOINTS];
    private int[] touchY = new int[MAX_TOUCHPOINTS];
    private int[] id = new int[MAX_TOUCHPOINTS];
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>();

    //FIN DE LO QUE NO HAY QUE TOCAR AUNQUE NO SIRVA PARA MUCHO

    public TouchHandler(View view) { //LO QUE CREA TOUCH EVENTS
        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };

        touchEventPool = new Pool<>(factory, 100);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) { //¿QUÉ HACE ESTO?
        synchronized (this) {
            int action = event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerCount = event.getPointerCount();

            int pointerId;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    pointerId = event.getPointerId(pointerIndex);
                    registerEvent(event, pointerIndex, pointerId, TouchType.TOUCH_DOWN);
                    break;
            }

            for (int i = pointerCount ; i < MAX_TOUCHPOINTS ; i++ ) {
                isTouched[i] = false;
                id[i] = -1;
            }
            return true;
        }
    }

    private void registerEvent(MotionEvent event, int i, int pointerId, TouchType type) {//¿QUÉ HACE ESTO?
        TouchEvent touchEvent = touchEventPool.newObject(); //CREA UN TOUCH EVENT
        touchEvent.type = type; //DE TIPO TYPE
        touchEvent.pointer = pointerId; //¿QUÉ ES EL POINTER Y POR QUÉ LO APUNTA AHÍ?
        touchEvent.x = touchX[i] = (int)event.getX(); //?
        touchEvent.y = touchY[i] = (int)event.getY();//?
        isTouched[i] = true;
        id[i] = pointerId;
        touchEventsBuffer.add(touchEvent);
    }

    public boolean isTouchDown(int pointer) { //ESTO ME DICE SI ESTOY TOCANDO
        synchronized (this) {
            int index = getIndex(pointer);
            return index >= 0 && index < MAX_TOUCHPOINTS && isTouched[index];
        }
    }

    public int getTouchX(int pointer) { //ESTO ME DA LA POSICIÓN X DE DÓNDE ESTOY TOCANDO
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[index];
        }
    }

    public int getTouchY(int pointer) { //ESTO ME DA LA POSICIÓN Y DE DÓNDE ESTOY TOCANDO
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[index];
        }
    }

    public List<TouchEvent> getTouchEvents() {//CREA UNA LISTA DE TOUCH EVENTS Y LA ACTUALIZA DE ALGÚN MODO DESCONOCIDO
        synchronized (this) {
            for (TouchEvent touchEvent : touchEvents)
                touchEventPool.free(touchEvent);
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    private int getIndex(int pointerId) {//ESTO ME DICE EL ÍNDICE DEL TOUCH EVENT EN LA LISTA PERO NO SÉ PARA QUÉ
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId)
                return i;
        }
        return -1;
    }
}
