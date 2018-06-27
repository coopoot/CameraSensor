package master.diegosalviano.com.appteste;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private Preview mPreview;

    private SensorManager sensorManager;
    private Sensor sensor;
    private float quality;

    private TextView tvResultado;

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {

        }
        return c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        tvResultado = (TextView) findViewById(R.id.tvResultado);

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                quality = event.values[0];
                Log.i("LOG", String.valueOf(quality));
                if (quality > 50) {
                    tvResultado.setText("ESTÁ CLARO");
                } else {
                    tvResultado.setText("ESTÁ ESCURO");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(
                listener, sensor, SensorManager.SENSOR_DELAY_UI);

        mCamera = getCameraInstance();

        mPreview = new Preview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera);
        preview.addView(mPreview);
    }
}
