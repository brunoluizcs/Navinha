package com.bomcodigo.navinha.game.control;

import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.scenes.GameScene;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class GameButtons extends CCLayer
        implements ButtonDelegate{
    private final String TAG = GameButtons.class.getSimpleName();
    private Button iceShootButton;
    private Button shootButton;
    private Button pauseButton;
    private GameScene delegate;

    public static GameButtons gameButtons(){
        return new GameButtons();
    }

    public GameButtons(){
        this.setIsTouchEnabled(true);
        this.iceShootButton = new Button(Assets.ICESHOOTBUTTON);
        this.shootButton = new Button(Assets.SHOOTBUTTON);
        this.pauseButton = new Button(Assets.PAUSE);


        this.shootButton.setDelegate(this);
        this.iceShootButton.setDelegate(this);
        this.pauseButton.setDelegate(this);

        setButtonsPosition();
        addChild(shootButton);
        addChild(iceShootButton);
        addChild(pauseButton);
    }

    private void setButtonsPosition() {
        iceShootButton.setPosition(CGPoint.ccp(40,40));
        shootButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()-40,40)));
        pauseButton.setPosition(screenResolution(CGPoint.ccp(40,screenHeight() - 30)));
    }

    public void setDelegate(GameScene delegate) {
        this.delegate = delegate;
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.shootButton)){
            Log.d(TAG,"Button clicked: Shotting!");
            this.delegate.shoot();
        }

        if (sender.equals(this.iceShootButton)){
            Log.d(TAG,"Button clicked: Shotting Ice !");
            this.delegate.shootGreen();
        }

        if (sender.equals(this.pauseButton)){
            this.delegate.pauseGameAndShowLayer();
        }
    }
}
