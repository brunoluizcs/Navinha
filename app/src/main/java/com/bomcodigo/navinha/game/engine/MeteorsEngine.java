package com.bomcodigo.navinha.game.engine;


import android.util.Log;

import com.bomcodigo.navinha.game.enums.MeteorType;
import com.bomcodigo.navinha.game.interfaces.MeteorsEngineDelegate;
import com.bomcodigo.navinha.game.object.Dificult;
import com.bomcodigo.navinha.game.object.Meteor;
import com.bomcodigo.navinha.game.screens.Runner;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

public class MeteorsEngine extends CCLayer{
    private final String TAG = MeteorsEngine.class.getSimpleName();
    private MeteorsEngineDelegate delegate;


    public MeteorsEngine() {
        this.schedule("meteorsEngine", 1.0f / 10f);
    }

    public void meteorsEngine(float dt){
        if (Runner.check().isGamePlaying() && ! Runner.check().isGamePaused()) {
            int probability = Dificult.sharedDificult().calcMeteorProbability();

            if (new Random().nextInt(probability) == 0){
                Log.d(TAG,"Meteor Probability: " + probability);
                this.getDelegate().createMeteor(new Meteor(MeteorType.Fire));
            }
            if (new Random().nextInt(probability) == 0){
                Log.d(TAG,"Meteor Probability: " + probability);
                this.getDelegate().createMeteor(new Meteor(MeteorType.Ice));
            }
        }
    }

    public MeteorsEngineDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }
}
