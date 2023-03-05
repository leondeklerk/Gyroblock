package nl.leontheclerk.gyroblock;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {
    private final SensorManager manager;
    private final Sensor accelerometer;
    private final Sensor magnetometer;

    private float[] accelOutput;
    private float[] magOutput;

    private final float[] orientation = new float[3];
    public float[] getOrientation(){
        return orientation;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation(){
        return startOrientation;
    }

    public OrientationData(Context context){
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register(){
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelOutput = event.values;
        } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magOutput = event.values;
        }

        if(accelOutput != null && magOutput != null){
            float[] R = new float[9];
            float[] I = new float[0];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);
            if(success){
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
