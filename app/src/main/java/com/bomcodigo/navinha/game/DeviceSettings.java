package com.bomcodigo.navinha.game;


import android.hardware.SensorManager;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class DeviceSettings {
    public static CGPoint screenResolution(CGPoint cgPoint){
        return cgPoint;
    }
    public static SensorManager sensorManager;

    public static CGSize winSize(){
        return CCDirector.sharedDirector().winSize();
    }

    public static float screenWidth(){
        return winSize().width;
    }
    public static  float screenHeight(){
        return winSize().height;
    }

    public static SensorManager getSensorManager() {
        return sensorManager;
    }

    public static void setSensorManager(SensorManager sensorManager) {
        DeviceSettings.sensorManager = sensorManager;
    }
}
