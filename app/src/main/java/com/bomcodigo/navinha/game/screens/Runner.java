package com.bomcodigo.navinha.game.screens;


public class Runner {

    private static boolean isGamePlaying;
    private static boolean isGamePaused;
    private static Runner runner = null;

    private Runner(){
    }

    public static Runner check(){
        if (runner != null){
            runner = new Runner();
        }
        return runner;
    }

    public static boolean isGamePlaying() {
        return isGamePlaying;
    }

    public static void setIsGamePlaying(boolean isGamePlaying) {
        Runner.isGamePlaying = isGamePlaying;
    }

    public static boolean isGamePaused() {
        return isGamePaused;
    }

    public static void setIsGamePaused(boolean isGamePaused) {
        Runner.isGamePaused = isGamePaused;
    }
}
