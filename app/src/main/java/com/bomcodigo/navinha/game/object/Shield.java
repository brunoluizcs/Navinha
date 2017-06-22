package com.bomcodigo.navinha.game.object;


import com.bomcodigo.navinha.game.Assets;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.nodes.CCSprite;

public class Shield extends CCSprite{

    public Shield() {
        super(Assets.SHIELD);
    }

    public void stopAnimation(){
        CCFadeOut out = CCFadeOut.action(1f);
        this.runAction(out);

    }
}
