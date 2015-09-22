package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;
import org.drl.lutz.sosmehringplatzapp.main.views.CameraPreview;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureImageActivity extends QuestionActivity {

    Camera mCamera;
    CameraPreview mCameraPreview;
    File mPictureFile;

    Submission submission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);


        FrameLayout preview = (FrameLayout) findViewById(R.id.preview);

        //adjust ratio
        //int h = preview.getHeight();
        //preview.getLayoutParams().width = (int)(h*4.0/3.0);

        //start camera
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        preview.addView(mCameraPreview);

        submission = (Submission)getIntent().getSerializableExtra("submission");
        this.setQuestionType(submission.type);
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            // open front facing camera
            camera = Camera.open(Camera.getNumberOfCameras()-1);
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    private static File getOutputMediaFileName() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public void onPreviewClicked(View view) {

        if (mPictureFile != null)
            return;

        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                File picture = getOutputMediaFileName();

                if (picture == null) {
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(picture);
                    fos.write(data);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                Log.e("CAMERA","Picture saved to "+picture.getAbsolutePath());
                mPictureFile = picture;
            }
        });
    }

    public void onAcceptButtonClicked(View view) {
        if (mPictureFile != null)
            submission.setImage(mPictureFile);

        Intent intent = new Intent();
        intent.putExtra("submission",submission);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void onCancelButtonClicked(View view) {

        // restart preview
        if (mPictureFile != null) {
            mPictureFile.delete();
            mPictureFile = null;
            mCamera.startPreview();
        }
    }
}
