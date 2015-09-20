package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

public class SelectInputActivity extends QuestionActivity {


    static final int REQUEST_TEXT_INPUT = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    static final int REQUEST_SOUND_CAPTURE = 3;

    QuestionType type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_input);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        type = (QuestionType)getIntent().getSerializableExtra("type");
        this.setQuestionType(type);

    }

    public void transitionNext() {

    }

    // start text input activity
    public void onTextButtonClicked(View view) {
        this.pauseIdleTimer(true);

        Intent intent = getIntent();
        intent.setClass(this, TextActivity.class);
        startActivityForResult(intent, REQUEST_TEXT_INPUT);
    }

    // start sound recording app
    public void onSoundButtonClicked(View view) {
        this.pauseIdleTimer(true);

        Intent takeSoundIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (takeSoundIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeSoundIntent, REQUEST_SOUND_CAPTURE);
        } else {
            showAlert("Error", "No Sound Recorder App found.");
        }
    }

    // start video app
    public void onVideoButtonClicked(View view) {
        this.pauseIdleTimer(true);

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra("android.intent.extra.durationLimit", 60);
        takeVideoIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            showAlert("Error", "No Video Recording App found.");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_TEXT_INPUT && resultCode == RESULT_OK) {
            String message = intent.getStringExtra("message");

            Submission submission = new Submission(type,message);
            Intent submitIntent = new Intent(this, SubmitActivity.class);
            submitIntent.putExtra("submission",submission);
            startActivity(submitIntent);
            finish();

        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Log.e("VIDEO", videoUri.toString());
        } else if (requestCode == REQUEST_SOUND_CAPTURE && resultCode == RESULT_OK) {
            Uri soundUri = intent.getData();
            Log.e("SOUND", soundUri.toString());
        } else {
            //this.showAlert(getResources().getString(R.string.fehler),getResources().getString(R.string.noSubmission));
        }

    }

}
