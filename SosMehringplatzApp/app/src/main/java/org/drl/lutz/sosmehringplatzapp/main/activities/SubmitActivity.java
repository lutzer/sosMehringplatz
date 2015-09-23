package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        EditText inputName = (EditText) findViewById(R.id.inputName);
        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onUserInteraction();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
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

        Intent cameraIntent = new Intent(this, CaptureImageActivity.class);
        cameraIntent.putExtra("submission", submission);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {

                //add image file to submission
                File image = (File) intent.getSerializableExtra("file");
                submission.image = image;

                Intent submitIntent = new Intent(this, UploadActivity.class);
                submitIntent.putExtra("submission", submission);
                startActivity(submitIntent);
                finish();
            } else {
                this.recreate();
            }
        }

    }
}
