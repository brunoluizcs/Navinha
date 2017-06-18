package com.bomcodigo.navinha.game.scenes;


import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.control.MenuButtons;
import com.bomcodigo.navinha.game.screens.ScreenBackground;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenHeight;
import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class TitleScreen extends CCLayer {
    private ScreenBackground background;

    public TitleScreen() {
        this.background = new ScreenBackground(Assets.BACKGROUND1);
        this.background.setPosition(
                screenResolution(CGPoint.ccp(
                        screenWidth() / 2.0f,
                        screenHeight() / 2.0f)));
        this.addChild(background);

        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2,screenHeight() - 130)));
        //this.addChild(title);

        MenuButtons menuButtons = new MenuButtons();
        this.addChild(menuButtons);
    }

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }
}
