package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

import java.io.File;

public class SubmitActivity extends QuestionActivity {

    Submission submission = null;

    int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        submission = (Submission)getIntent().getSerializableExtra("submission");
        this.setQuestionType(submission.type);
    }

    public void onAnonymousButtonClicked(View view) {
        Intent uploadIntent = new Intent(this,UploadActivity.class);
        uploadIntent.putExtra("submission",submission);
        startActivity(uploadIntent);
        finish();
    }

    public void onFaceButtonClicked(View view) {

        findViewById(R.id.anonymousButtonLayout).setVisibility(View.GONE);
        findViewById(R.id.faceButtonLayout).setVisibility(View.GONE);

        findViewById(R.id.inputNameLayout).setVisibility(View.VISIBLE);


    }

    public void onNameAcceptClicked(View view) {

        EditText inputName = (EditText)findViewById(R.id.inputName);
        submission.author = inputName.getText().toString();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            showAlert("Error", "No Photo App found.");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //add image file to submission
            File image = (File)intent.getSerializableExtra("image");
            submission.image = image;

            Intent submitIntent = new Intent(this, UploadActivity.class);
            submitIntent.putExtra("submission", submission);
            startActivity(submitIntent);
            finish();
        }

    }
}
