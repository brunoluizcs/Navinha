package com.bomcodigo.navinha.game.object;


import android.util.Log;

import com.bomcodigo.navinha.game.scenes.GameScene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class Score extends CCLayer{
    private final String TAG = Score.class.getSimpleName();
    private int score;
    private CCBitmapFontAtlas text;
    private GameScene delegate;

    public void setDelegate(GameScene delegate) {
        this.delegate = delegate;
    }

    public Score(){
        this.score = 0;
        this.text = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(this.score),"UniSansSemiBold_Numbers_240.fnt");
        this.text.setScale((float) 100/100);
        this.setPosition(screenWidth()-50,screenHeight() - 50);
        this.addChild(this.text);
    }

    public void increase(){
        score++;
        Log.d(TAG,"Score: " + score);
        this.text.setString(String.valueOf(this.score));
        if (score==100){
            Log.d(TAG,"Player Win !");
            this.delegate.startFinalScreen();
        }
    }
}
