package spiritinlife.gr.goodcam;

import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;

import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Spiritinlife on 31/1/2015.
 */
public class MainActivity extends Activity implements SurfaceHolder.Callback {

    TextView textView;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    final CameraDevice[] cd = new CameraDevice[1];
    GCCameraManager gcCameraManager;


    int[] validWidth = { 1280, 800, 720, 640, 576, 480, 384, 352, 320, 240, 176};
    int[] validHeight = {960, 720,480, 432, 320, 288, 240, 160, 144 };


    private int nearestWidth(int width)
    {
        switch(width/100)
        {
            case 1:
                return 176;
            case 2:
                return 240;
            case 3:
                switch (width/10)
                {
                    case 32:
                        return 320;
                    case 352:
                        return 352;
                    case 384:
                        return 384;
                    default:
                        return 352;
                }
            case 4:
                return 480;
            case 5:
                return 576;
            case 6:
                return 640;
            case 7:
                return 720;
            case 8:
            case 9:
                return 800;
            case 10:
            case 11:
            case 12:
                return 1280;
        };

        return 1280;
    }

    private int nearestHeight(int height)
    {
        switch(height/100)
        {
            case 1:
                return 144;
            case 2:
                return 160;
            case 3:
               return 320;
            case 4:
                return 480;
            case 5:
                return 576;
            case 6:
                return 576;
            case 7:
                return 720;
            case 8:
            case 9:
                return 960;
        };

        return 960;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        gcCameraManager = new GCCameraManager(this);

        String [] x = gcCameraManager.getCameraIds();



        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(MainActivity.this);






    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {

        gcCameraManager.openCamera("0",new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                cd[0] = camera;
                // Install a SurfaceHolder.Callback so we get notified when the
                // underlying surface is created and destroyed.
                // deprecated setting, but required on Android versions prior to 3.0
                CameraCharacteristics characteristics = gcCameraManager.getCameraCharacteristics("0");
                StreamConfigurationMap configs = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size[] sizes = configs.getOutputSizes(SurfaceHolder.class);
                Log.e("AAAA",""+nearestWidth(sizes[0].getWidth()) + "   " + nearestHeight(sizes[0].getHeight()));
                holder.setFixedSize(nearestWidth(sizes[0].getWidth()),nearestHeight(sizes[0].getHeight()));

                List<Surface> surfaces = new ArrayList<Surface>();
                surfaces.add(holder.getSurface());
                try {
                    cd[0].createCaptureSession(surfaces,new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {

                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {

                        }
                    },null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDisconnected(CameraDevice camera) {

            }

            @Override
            public void onError(CameraDevice camera, int error) {

            }
        },null);




         }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
