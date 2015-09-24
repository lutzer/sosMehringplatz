package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;
import org.drl.lutz.sosmehringplatzapp.main.utils.SoundRecorder;
import org.drl.lutz.sosmehringplatzapp.main.utils.SoundRecorderWav;

import java.io.File;

public class SoundRecorderActivity extends QuestionActivity {

    public enum RecorderState {
        INIT, RECORDING, STOPPED
    }

    RecorderState state = RecorderState.INIT;
    CountDownTimer timer = null;
    SoundRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_recorder);

        recorder = new SoundRecorderWav(getApplicationContext());
        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        QuestionType type = (QuestionType)getIntent().getSerializableExtra("type");
        this.setQuestionType(type);
    }

    @Override
    protected void onStop() {
        super.onStop();
        resetRecording();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recorder.release();
    }

    public void startRecording() {

        findViewById(R.id.stopButton).setVisibility(View.VISIBLE);
        findViewById(R.id.recordButton).setVisibility(View.GONE);

        //show Record Icon
        final View recordIcon = findViewById(R.id.recordIcon);
        recordIcon.setVisibility(View.VISIBLE);

        //start flashing record icon
        final Animation flashAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flash);
        recordIcon.startAnimation(flashAnimation);

        //start Counting Time, set maximum time
        timer = new CountDownTimer(1000 * 300, 10) {

            long startTime = System.currentTimeMillis();

            public void onTick(long millisUntilFinished) {
                long elapsedTime = System.currentTimeMillis() - startTime;

                //update time
                TextView view = (TextView)findViewById(R.id.recordTimeView);
                view.setText(convertTimeString(elapsedTime));
            }

            public void onFinish() {
                stopRecording();
            }

        }.start();

        try {
            recorder.prepare();
            recorder.startRecording();
        } catch (Exception e) {
            stopRecording();
            showAlert("Error", "Error: " + e.toString());
        }

        state = RecorderState.RECORDING;

        pauseIdleTimer(true);
    }

    public void stopRecording() {

        findViewById(R.id.stopButton).setVisibility(View.GONE);
        findViewById(R.id.recordButton).setVisibility(View.GONE);
        findViewById(R.id.acceptButton).setVisibility(View.VISIBLE);
        findViewById(R.id.restartButton).setVisibility(View.VISIBLE);

        //hide Record Icon
        View recordIcon = findViewById(R.id.recordIcon);
        recordIcon.clearAnimation();
        recordIcon.setVisibility(View.INVISIBLE);

        //stop timer
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        recorder.stopRecording();

        state = RecorderState.STOPPED;

        pauseIdleTimer(false);
    }

    public void resetRecording() {

        findViewById(R.id.stopButton).setVisibility(View.GONE);
        findViewById(R.id.recordButton).setVisibility(View.VISIBLE);
        findViewById(R.id.acceptButton).setVisibility(View.GONE);
        findViewById(R.id.restartButton).setVisibility(View.GONE);

        //reset Time
        TextView timeView = (TextView)findViewById(R.id.recordTimeView);
        timeView.setText(convertTimeString(0));

        recorder.reset();
        state = RecorderState.INIT;
    }

    private String convertTimeString(long milliseconds) {

        long hsecs= (milliseconds % 1000) / 100;
        long secs = (milliseconds / 1000) % 60 ;
        long mins = milliseconds / (1000 * 60);

        String secString = Long.toString(secs);
        if (secs < 10)
            secString = "0"+secString;

        String minsString = Long.toString(mins);
        if (mins < 10)
            minsString = "0"+minsString;

        return(minsString+":"+secString);
    }

    public void onRecordButtonClicked(View view) {

        startRecording();

    }

    public void onStopButtonClicked(View view) {

        stopRecording();
    }

    public void onRestartButtonClicked(View view) {
        resetRecording();
    }

    public void onAcceptButtonClicked(View view) {

        File recordedFile;
        try {
            recordedFile = recorder.save();
            if (recordedFile == null)
                throw new Exception("file is null.");
        } catch (Exception e) {
            this.showAlert("Error", "Error writing sound file: " + e.toString());
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("recording",recordedFile);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }


    public void onCancelButtonClicked(View view) {
        resetRecording();
        finish();
    }

}
