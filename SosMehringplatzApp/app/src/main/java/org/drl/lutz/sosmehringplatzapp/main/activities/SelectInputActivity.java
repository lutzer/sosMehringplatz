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

import java.io.File;

public class SelectInputActivity extends QuestionActivity {


    static final int REQUEST_TEXT_INPUT = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    static final int REQUEST_SOUND_CAPTURE = 3;

    Submission submission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_input);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        QuestionType type = (QuestionType)getIntent().getSerializableExtra("type");
        submission = new Submission(type);
        this.setQuestionType(type);

    }

    public void transitionNext() {

    }

    // start text input activity
    public void onTextButtonClicked(View view) {
        this.pauseIdleTimer(true);

        Intent intent = new Intent(this,TextActivity.class);
        intent.putExtra("type",submission.type);
        startActivityForResult(intent, REQUEST_TEXT_INPUT);
    }

    // start sound recording app
    public void onSoundButtonClicked(View view) {
        this.pauseIdleTimer(true);

        Intent intent = new Intent(this,SoundRecorderActivity.class);
        intent.putExtra("type",submission.type);
        startActivityForResult(intent, REQUEST_SOUND_CAPTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        this.pauseIdleTimer(false);

        if (requestCode == REQUEST_TEXT_INPUT && resultCode == RESULT_OK) {

            String message = intent.getStringExtra("message");
            submission.setMessage(message);

            Intent submitIntent = new Intent(this, SubmitActivity.class);
            submitIntent.putExtra("submission",submission);
            startActivity(submitIntent);
            finish();

        } else if (requestCode == REQUEST_SOUND_CAPTURE && resultCode == RESULT_OK) {
            File recording = (File)intent.getSerializableExtra("recording");
            submission.setRecording(recording);

            Intent submitIntent = new Intent(this, SubmitActivity.class);
            submitIntent.putExtra("submission",submission);
            startActivity(submitIntent);
            finish();
        } else {
            //this.showAlert(getResources().getString(R.string.fehler),getResources().getString(R.string.noSubmission));
        }

    }

    public void onCancelButtonClicked(View view) {
        finish();
    }
}
