package com.bomcodigo.navinha.game.object;


import android.util.Log;

public class Dificult {
    private final String TAG = Dificult.class.getSimpleName();
    private static Dificult instance = null;

    private Dificult() {
    }

    public static Dificult sharedDificult(){
        if (instance == null){
            instance = new Dificult();
        }
        return instance;
    }

    public int calcMeteorProbability(){
        int base = 20;
        float score = Score.sharedScore().getScore();
        float tax = Math.round( (score / 200) * base);
        int probability = Math.round(base - tax);
        return probability <= 0 ? 1 : probability;
    }


}
