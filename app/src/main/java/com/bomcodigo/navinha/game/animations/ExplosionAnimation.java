package com.bomcodigo.navinha.game.animations;


import com.bomcodigo.navinha.game.Assets;
import com.bomcodigo.navinha.game.interfaces.AnimationEngine;

import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCIntervalAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class ExplosionAnimation implements AnimationEngine{
    private CCIntervalAction action;

    public ExplosionAnimation() {
        CCAnimation animation = CCAnimation.animation("explosion",2 / 20f);
        CCSpriteSheet spriteSheet = CCSpriteSheet.spriteSheet(Assets.EXPLOSION);
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                animation.addFrame(CCSpriteFrame.frame(spriteSheet.getTexture(), CGRect.make(x*70,y*70,70,70), CGPoint.ccp(0,0)));
            }
        }
        this.action = CCAnimate.action(0.5f, animation, false);
    }

    @Override
    public CCIntervalAction getAction() {
        return this.action;
    }
}
