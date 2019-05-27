package com.example.andy.sudokudefinitivoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends GameActivity {


    public int sudokusize = 900;
    public int margensudoku = 10;


    @Override
    protected IGameController buildGameController() {
        //no consigo crear una variable pública por aquí a la que luego pueda acceder desde sudoku
        //para que se dibuje entero en relación a una variable en lugar de a un entero, así que
        //en la propia definición del sudoku (en sudoku1) voy a hacer que sea cual sea el valor que
        //le pase desde aquí, lo transforme en el que, de momento, necesito :)
        int sudokutotalsize = sudokusize + 2*margensudoku;
        return new Sudoku1(900, 900);
    }

    public void startActivity(View view) {
        Intent intent = new Intent (this, Sudoku1.class);
        super.startActivity(intent);
    }
}
