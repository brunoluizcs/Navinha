package com.bomcodigo.navinha.game.control;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.bomcodigo.navinha.game.DeviceSettings;
import com.bomcodigo.navinha.game.interfaces.AccelerometerDelegate;

public class Accelerometer implements SensorEventListener{
    private float currentAccelerationX;
    private float currentAccelerationY;
    private float calibrateAccelerationX;
    private float calibrateAccelerationY;

    private static Accelerometer sharedAccelerometer = null;
    private AccelerometerDelegate delegate;
    private SensorManager sensorManager;
    private int calibrated = 0;

    public Accelerometer(){
        this.catchAccelerometer();
    }

    public static Accelerometer sharedAccelerometer(){
        if (sharedAccelerometer == null){
            sharedAccelerometer = new Accelerometer();
        }
        return sharedAccelerometer;
    }

    @Override
    public void onSensorChanged(SensorEvent acceleration) {
        if (this.calibrated<100){
            this.calibrateAccelerationX += acceleration.values[0];
            this.calibrateAccelerationY += acceleration.values[1];

            this.calibrated++;
            if (calibrated == 100){
                this.calibrateAccelerationX /=100;
                this.calibrateAccelerationY /= 100;
            }
            return;
        }

        this.currentAccelerationX = acceleration.values[0] - this.calibrateAccelerationX;
        this.currentAccelerationY = acceleration.values[1] - this.calibrateAccelerationY;
        if (this.delegate != null){
            this.delegate.accelerometerDidAccelerate(
                    currentAccelerationX,currentAccelerationY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public AccelerometerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(AccelerometerDelegate delegate) {
        this.delegate = delegate;
    }

    public void catchAccelerometer(){
        this.calibrated = 0;
        sensorManager = DeviceSettings.getSensorManager();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }
}
