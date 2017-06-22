package com.bomcodigo.navinha.game.object;


import android.util.Log;

import com.bomcodigo.navinha.game.scenes.GameScene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Score extends CCLayer{
    private static Score instance = null;
    private final String TAG = Score.class.getSimpleName();
    private int score;
    private int combo;
    private CCBitmapFontAtlas text;
    private GameScene delegate;


    public void setDelegate(GameScene delegate) {
        this.delegate = delegate;
    }

    private Score(){
        this.score = 0;
        this.combo = 0;
        this.text = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(this.score),"UniSansSemiBold_Numbers_240.fnt");
        this.text.setScale(0.7f);
        this.setPosition(screenWidth()-50,screenHeight() - 50);
        this.addChild(this.text);
    }

    public static Score sharedScore(){
        if (instance == null){
            instance = new Score();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void clear(){
        this.score = 0;
        this.combo = 0;
        this.text.setString(String.valueOf(this.score));
    }

    public void increase(){
        this.score++;
        this.combo++;
        Log.d(TAG,"Score: " + score);
        this.text.setString(String.valueOf(this.score));
    }

    public void decrease(){
        this.combo = 0;
        if (this.score > 0) {
            this.score--;
            Log.d(TAG, "Score: " + this.score);
            this.text.setString(String.valueOf(this.score));
        }
    }

    public int getCombo() {
        return combo;
    }
}
