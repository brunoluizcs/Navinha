package com.bomcodigo.navinha.game.engine;


import org.cocos2d.types.CGRect;

public class ColisionEngine {

    public CGRect reduceRadiosArea(CGRect rect,float factor){
        float w = rect.size.width - ( rect.size.width * (factor/100) );
        float h = rect.size.height - ( rect.size.height * (factor/100) );
        rect.size.set(w,h);
        return rect;
    }
}
