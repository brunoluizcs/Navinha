package com.bomcodigo.navinha.game.control;

import android.util.Log;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.services.GameService;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class SignInButtons extends CCLayer
        implements ButtonDelegate {
    private static final String TAG = SignInButtons.class.getSimpleName();

    private Button signInButton;

    public SignInButtons(){
        this.setIsTouchEnabled(true);
        this.signInButton = new Button(Assets.PLAY);
        this.signInButton.setDelegate(this);
        setButtonsPosition();
        addChild(signInButton);
    }

    public void setButtonsPosition(){
        signInButton.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2 , screenHeight() - 250)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.signInButton)){
            Log.d(TAG,"Button clicked: SignIn");
            GameService.sharedGameService().connect();
        }
    }


}
