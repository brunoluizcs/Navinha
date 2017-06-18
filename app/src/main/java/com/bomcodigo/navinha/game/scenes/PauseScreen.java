package com.bomcodigo.navinha.game.scenes;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.Button;
import com.bomcodigo.navinha.game.interfaces.ButtonDelegate;
import com.bomcodigo.navinha.game.interfaces.PauseDelegate;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;


public class PauseScreen extends CCLayer implements ButtonDelegate {

    private Button resumeButton;
    private Button quitButton;
    private CCColorLayer background;
    private PauseDelegate delegate;

    public PauseScreen() {
        this.setIsTouchEnabled(true);

        this.background = CCColorLayer.node(ccColor4B.ccc4(0,0,0,175),screenWidth(),screenHeight());
        this.addChild(this.background);

        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2,screenHeight() - 130)));
        //this.addChild(title);

        this.resumeButton = new Button(Assets.PLAY);
        this.quitButton = new Button(Assets.EXIT);

        this.resumeButton.setDelegate(this);
        this.quitButton.setDelegate(this);

        this.addChild(resumeButton);
        this.addChild(quitButton);

        this.resumeButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 250)));
        this.quitButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 300)));
    }

    public void setDelegate(PauseDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(resumeButton)){
            this.delegate.resumeGame();
            this.removeFromParentAndCleanup(true);
        }

        if (sender.equals(quitButton)){
            this.delegate.quitGame();
        }

    }
}
