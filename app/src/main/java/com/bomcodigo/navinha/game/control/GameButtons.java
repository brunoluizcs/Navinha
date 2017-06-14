package com.bomcodigo.navinha.game.control;

import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class GameButtons extends CCLayer
        implements ButtonDelegate{
    private final String TAG = GameButtons.class.getSimpleName();

    private Button leftControl;
    private Button rightControl;
    private Button shootButton;

    public static GameButtons gameButtons(){
        return new GameButtons();
    }

    public GameButtons(){
        this.setIsTouchEnabled(true);

        this.leftControl = new Button(Assets.LEFTCONTROL);
        this.leftControl.setDelegate(this);
        this.rightControl = new Button(Assets.RIGHTBUTTON);
        this.rightControl.setDelegate(this);
        this.shootButton = new Button(Assets.SHOOTBUTTON);
        this.shootButton.setDelegate(this);

        setButtonsPosition();

        addChild(leftControl);
        addChild(rightControl);
        addChild(shootButton);
    }

    private void setButtonsPosition() {
        leftControl.setPosition(screenResolution(CGPoint.ccp(40,40)));
        rightControl.setPosition(screenResolution(CGPoint.ccp(100,40)));
        shootButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()-40,40)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.leftControl)){
            Log.d(TAG,"Button clicked: left");
        }
        if (sender.equals(this.rightControl)){
            Log.d(TAG,"Button clicked: right");
        }
        if (sender.equals(this.shootButton)){
            Log.d(TAG,"Button clicked: Shotting!");
        }
    }
}