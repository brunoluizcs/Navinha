package com.bomcodigo.navinha.game.screens;


import com.bomcodigo.navinha.game.Assets;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import static com.bomcodigo.navinha.game.DeviceSettings.screenResolution;
import static com.bomcodigo.navinha.game.DeviceSettings.screenWidth;

public class ParallaxBackground extends CCLayer {
    private final int MAXHEIGHT = 948;
    private CCSprite _oddBackground;
    private CCSprite _evenBackground;


    public ParallaxBackground() {
        this._oddBackground = new ScreenBackground(Assets.PARALLAX1);
        this._evenBackground = new ScreenBackground(Assets.PARALLAX1);

        this._oddBackground.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, MAXHEIGHT / 2.0f)));
        this._evenBackground.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2.0f, MAXHEIGHT + (MAXHEIGHT /2))));


        this.addChild(this._oddBackground).addChild(this._evenBackground);
    }

    public void scroll(float dt) {
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            this._oddBackground.setPosition(this._oddBackground.getPosition().x,
                    this._oddBackground.getPosition().y - 35 * dt);

            this._evenBackground.setPosition(this._evenBackground.getPosition().x,
                    this._evenBackground.getPosition().y - 35 * dt);

            if (this._oddBackground.getPosition().y < -MAXHEIGHT /2) {
                this._oddBackground.setPosition(screenWidth() / 2, MAXHEIGHT / 2);
                this._evenBackground.setPosition(screenWidth() / 2, MAXHEIGHT + (MAXHEIGHT / 2));
            }
        }
    }
}
