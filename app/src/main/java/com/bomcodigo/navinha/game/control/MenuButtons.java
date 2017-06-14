package com.bomcodigo.navinha.game.control;

import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.scenes.GameScene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class MenuButtons extends CCLayer
        implements ButtonDelegate {
    private static final String TAG = MenuButtons.class.getSimpleName();

    private Button playButton;
    private Button highscoreButton;
    private Button helpButton;
    private Button soundButton;

    public MenuButtons(){
        this.setIsTouchEnabled(true);
        this.playButton = new Button(Assets.PLAY);
        this.playButton.setDelegate(this);

        this.highscoreButton = new Button(Assets.HIGHSCORE);
        this.highscoreButton.setDelegate(this);

        this.helpButton = new Button(Assets.HELP);
        this.helpButton.setDelegate(this);

        this.soundButton = new Button(Assets.SOUND);
        this.soundButton.setDelegate(this);

        setButtonsPosition();

        addChild(playButton);
        addChild(highscoreButton);
        addChild(helpButton);
        addChild(soundButton);
    }

    public void setButtonsPosition(){
        playButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 250)));
        highscoreButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 300)));
        helpButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 350)));
        soundButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 -100 , screenHeight() - 420)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.playButton)){
            Log.d(TAG,"Button clicked: Play");
            CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1.0f,
                    GameScene.createGame()));
        }
        if (sender.equals(this.highscoreButton)){
            Log.d(TAG,"Button clicked: Highscore");
        }
        if (sender.equals(this.helpButton)){
            Log.d(TAG,"Button clicked: Help");
        }
        if (sender.equals(this.soundButton)){
            Log.d(TAG,"Button Clicked: Sound");
        }
    }
}
