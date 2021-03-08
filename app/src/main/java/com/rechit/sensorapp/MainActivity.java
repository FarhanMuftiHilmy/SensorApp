package com.rechit.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText;
    private SensorManager sensorManager;
    private TextView sensorAcceleromaterText;
    private TextView sensorProximityText;
    private Sensor sensorAcceleromater;
    private Sensor sensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText = findViewById(R.id.sensor_list);
        sensorAcceleromaterText = findViewById(R.id.sensor_acc);
        sensorProximityText = findViewById(R.id.sensor_proximity);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAcceleromater = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL); // get all sensor data
        StringBuilder sensorTx = new StringBuilder();
        for(Sensor sensor:sensorList){
            sensorTx.append(sensor.getName()+"\n");
        }
        sensorText.setText(sensorTx.toString());

        // check if the device sensor availability
        if(sensorAcceleromater==null){
            Toast.makeText(this, "No sensor acceleromater available", Toast.LENGTH_SHORT).show();
        }
        if(sensorProximity==null){
            Toast.makeText(this, "No sensor proximity available", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sensorAcceleromater!=null){
            sensorManager.registerListener(this, sensorAcceleromater, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorProximity!=null){
            sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType=sensorEvent.sensor.getType(); // get sensor by type
        float value=sensorEvent.values[0];
        if(sensorType==Sensor.TYPE_ACCELEROMETER){
            sensorAcceleromaterText.setText(String.format("Accelerometer Sensor: %1$.2f", value));
        }
        if(sensorType==Sensor.TYPE_PROXIMITY){
            sensorProximityText.setText(String.format("Proximity Sensor: %1$.2f", value));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}