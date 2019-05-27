package com.example.andy.sudokudefinitivoapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import java.util.List;

/**
 * Created by Andy on 24/05/2016.
 */
public class Sudoku1 implements IGameController { //sí, necesito tooooooodas esas variables
    private int width;
    private int height;
    public int sudokusize;
    public int sudokumargen;
    public int cellsize;
    private Graphics graphics;
    int size;
    Canvas canvas = new Canvas();



    public Sudoku1(int ancho, int alto) {
        sudokusize = 900;
        sudokumargen = 10;
        cellsize = sudokusize / 9;
        ancho = sudokusize + 2 * sudokumargen;
        alto = sudokusize + 2 * sudokumargen;
        this.width = ancho;
        this.height = alto;


        graphics = new Graphics(this.width, this.height);

    }


    public void onUpdate(float deltaTime, List<TouchHandler.TouchEvent> touchEvents) {

        boolean touching = false;

        for (int i = 0; i < touchEvents.size(); i++) { //recorre la lista de touch events
            if ((touchEvents.get(i).type == TouchHandler.TouchType.TOUCH_DOWN)) {
                touching = true;

                // convierte el centro de coordenadas en el centro del tablero

                int touchx = touchEvents.get(i).x - width / 2;
                int touchy = touchEvents.get(i).y - height / 2;
                if ((touchx) < 0) touchx = -touchx;
                if ((touchy) < 0) touchy = -touchy;

            } else touching = false;
        }
    }

    public Bitmap onDrawingRequested() {
        graphics.clear(0xffffff);
        int i;
        for (i = 0; i < 10; i++) {

            //HORIZONTALES

            graphics.drawLine(sudokumargen, sudokumargen + i * cellsize, sudokumargen + sudokusize, sudokumargen + i * cellsize, 0xff000000);

            //hacer los bordes más gordetes
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                graphics.drawLine(sudokumargen, sudokumargen + 1 + i * cellsize, sudokumargen + sudokusize, sudokumargen + 1 + i * cellsize, 0xff000000);
                graphics.drawLine(sudokumargen, sudokumargen - 1 + i * cellsize, sudokumargen + sudokusize, sudokumargen - 1 + i * cellsize, 0xff000000);
            }
            //VERTICALES

                graphics.drawLine(sudokumargen + i * cellsize, sudokumargen, sudokumargen + i * cellsize, sudokumargen + sudokusize, 0xff000000);

            //hacer los bordes más gordetes
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                graphics.drawLine(sudokumargen+1 + i * cellsize, sudokumargen, sudokumargen+1 + i * cellsize, sudokumargen + sudokusize, 0xff000000);
                graphics.drawLine(sudokumargen-1 + i * cellsize, sudokumargen, sudokumargen-1 + i * cellsize, sudokumargen + sudokusize, 0xff000000);
            }
        }
         //   graphics.drawText("9", 50, 50, 100, Color.RED);
        //no sé si esto va aquí o detrás de lo de paint

            InsertSudoku("4...3.......6..8..........1....5..9..8....6...7.2........1.27..5.3....4.9........");

            return graphics.getFrameBuffer();

        //intento de párrafo para escribir el texto
        //no me deja importar canvas POR QUÉ NO ME DEJA IMPORTAR CANVAS????

       //ESTA PUTA MIERDA NO VA
        /*Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        canvas.drawText("Some Text", 10, 25, paint);
        //drawtext (puntuación ujibubbles)
        */

    }

    public int[][] crearmatriz(String cadena) {
        int k = 0;
        int[][] matriz = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cadena.charAt(k) != '.') {
                    matriz[i][j] = cadena.charAt(k) - '0';
                } else
                    matriz[i][j] = 0;
                k++;
            }
        }
        return matriz;
    }

    public void InsertSudoku(String string){
        int[][]matriz = crearmatriz(string);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matriz[i][j]!=0) {
                    graphics.drawText(String.valueOf(matriz[i][j]), i*cellsize+3*sudokumargen, cellsize+j*cellsize, 100, Color.RED);
                }
            }
        }
       // return graphics.getFrameBuffer();
    }
}

/*
MARAVILLOSA FUNCION PARA COMPROBAR LAS COSAS REPETIDAS EN UN SUDOKU SUPONIENDO QUE ME PASAN UN STRING DE 81 CARACTERES

Funcionamiento general:

Cada vez que insterto un numero:
    si hay errores
        marcar en rojo
    else
        si esta llena la cadena
            he ganado

     mini base de datos de arrays para que los checkers vayan mas rapido:

     int columna0[]={0, 9, 18, 27, 36, 45, 54, 63, 72};
     int columna1[]={1, 10, 19, 28, 37, 46, 55, 64, 73};
     int columna2[]={2, 11, 20, 29, 38, 47, 56, 65, 74};
     int columna3[]={3, 12, 21, 30, 39, 48, 57, 66, 75};
     int columna4[]={4, 13, 22, 31, 40, 49, 58, 67, 76};
     int columna5[]={5, 14, 23, 32, 41, 50, 59, 68, 77};
     int columna6[]={6, 15, 24, 33, 42, 51, 60, 69, 78};
     int columna7[]={7, 16, 25, 34, 43, 52, 61, 70, 79};
     int columna8[]={8, 17, 26, 35, 44, 53, 62, 71, 80};
     int cuadro0[]={0, 1, 2, 9, 10, 11, 18, 19, 20};
     int cuadro1[]={3, 4, 5, 12, 13, 14, 21, 22, 23};
     int cuadro2[]={6, 7, 8, 15, 16, 17, 24, 25, 26};
     int cuadro3[]={27, 28, 29, 36, 37, 38, 45, 46, 47};
     int cuadro4[]={30, 31, 32, 39, 40, 41, 48, 49, 51};
     int cuadro5[]={33, 34, 35, 42, 43, 44, 51, 52, 53};
     int cuadro6[]={54, 55, 56, 63, 64, 65, 72, 73, 74};
     int cuadro7[]={57, 58, 59, 66, 67, 68, 75, 76, 77};
     int cuadro8[]={60, 61, 62, 69, 70, 71, 78, 79, 80};
     int fila0[]={0, 1, 2, 3, 4, 5, 6, 7, 8};
     int fila1[]={9, 10, 11, 12, 13, 14, 15, 16, 17};
     int fila2[]={18, 19, 20, 21, 22, 23, 24, 25, 26};
     int fila3[]={27, 28, 29, 30, 31, 32, 33, 34, 35};
     int fila4[]={36, 37, 38, 39, 40, 41, 42, 43, 44};
     int fila5[]={45, 46, 47, 48, 49, 50, 51, 52, 53};
     int fila6[]={54, 55, 56, 57, 58, 59, 60, 61, 62};
     int fila7[]={63, 64, 65, 66, 67, 68, 69, 70, 71};
     int fila8[]={72, 73, 74, 75, 76, 77, 78, 79, 80};

     Sabiendo el indice del numero que quiero poner pero todavia no he puesto:

     private void checkcolumn(int index, int number){

        column = index%9;
        //creo que debo inicializar el array usefulcolumn pero no estoy seguro

        switch (column){
            case 0: usefulcolumn = columna0;
                    break;
            case 1: usefulcolumn = columna1;
                    break;
            case 2: usefulcolumn = columna2;
                    break;
            case 3: usefulcolumn = columna3;
                    break;
            case 4: usefulcolumn = columna4;
                    break;
            case 5: usefulcolumn = columna5;
                    break;
            case 6: usefulcolumn = columna6;
                    break;
            case 7: usefulcolumn = columna7;
                    break;
            case 8: usefulcolumn = columna8;
                    break;
            default: break;
            }
        for (i=0; i<9; i++){
            if (number == usefulcolumn[i]){
                string[index] = number;
                string[index] = color rojo;
                string[usefulcolumn[i]] = color rojo;
                }
            }
        if (string[index] != number){
            string[index] = number;
            }
}

     private void checkrow(int index, int number){

        row = index/9;
        //creo que debo inicializar el array usefulrow pero no estoy seguro

        switch (row){
            case 0: usefulrow = fila0;
                    break;
            case 1: usefulrow = fila1;
                    break;
            case 2: usefulrow = fila2;
                    break;
            case 3: usefulrow = fila3;
                    break;
            case 4: usefulrow = fila4;
                    break;
            case 5: usefulrow = fila5;
                    break;
            case 6: usefulrow = fila6;
                    break;
            case 7: usefulrow = fila7;
                    break;
            case 8: usefulrow = fila8;
                    break;
            default: break;
            }
        for (i=0; i<9; i++){
            if (number == usefulrow[i]){
                string[index] = number;
                string[index] = color rojo;
                string[usefulrow[i]] = color rojo;
                }
            }
        if (string[index] != number){
            string[index] = number;
            }
}

     private void checksquare(int index, int number){

        //creo que debo inicializar el array usefulsquare pero no estoy seguro
        int column = index % 9;
        int row = index / 9;

        if (column<3){
            if (row<3){usefulsquare = cuadro0}
            else{if (row<6){usefulsquare = cuadro3}
                else{usefulsquare = cuadro6}
                }else{
        if (column<6){
            if (row<3){usefulsquare = cuadro1}
            else{if (row<6){usefulsquare = cuadro4}
                else{usefulsquare = cuadro7}
                }else{
        if (row<3){usefulsquare = cuadro2}
        else{if (row<6){usefulsquare = cuadro5}
                else{usefulsquare = cuadro8}

            }
        for (i=0; i<9; i++){
            if (number == usefulsquare[i]){
                string[index] = number;
                string[index] = color rojo;
                string[usefulsquare[i]] = color rojo;

                }
            }
        if (string[index] != number){
            string[index] = number;
            }
}

cada vez que se toque una casilla del sudoku en pantalla se debe:
    pedir un número del 1 al 9
    transformar posición en el índice de la casilla tocada
    llamar a la función:

    void checknadd(int index, int number){
        checkcoulmn(index, number);
        checkrow(index, number);
        checksquare(index, number);
        }

    pasándole el número y el indice como argumentos
    comprobar si el sudoku está lleno con la función:

    boolean lleno(String string){
        recorrer string
            si no esta lleno
                return false
            else
                return true

    si está lleno, hacer un último checkeo de que no hay errores con la función:



    boolean lastcheck

 */
