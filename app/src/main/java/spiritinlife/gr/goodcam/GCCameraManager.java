package spiritinlife.gr.goodcam;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;

/**
 * Created by Spiritinlife on 31/1/2015.
 * Basic encapsulation of Camera Manager methods
 */
public class GCCameraManager {

    private static final String TAG = "GCCameraManager";
    private CameraManager cameraManager;




    public GCCameraManager(Context context) {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }


    /**
     * This method is used to get full list of camera devices
     * @return String[]
     * @see @link getCameraCharacteristics
     */
    public String[] getCameraIds()
    {
        try {
            return cameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    /**
     * This method is used to get the characteristics of a camera
     * @param cameraId @tag String
     * @return @link CameraCharacteristics
     */
    public CameraCharacteristics getCameraCharacteristics (String cameraId)
    {
        try {
            return cameraManager.getCameraCharacteristics(cameraId);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * This methods is used to create a CameraDevice
     * @param cameraId @type String
     * @param callback @type CameraDevice.StateCallback
     * @param handler  @type Handler
     */
    public void openCamera (String cameraId, CameraDevice.StateCallback callback, Handler handler){
        try {
            cameraManager.openCamera(cameraId,callback,handler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}