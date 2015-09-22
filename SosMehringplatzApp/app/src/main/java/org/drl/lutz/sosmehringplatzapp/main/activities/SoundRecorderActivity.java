package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.os.CountDownTimer;
import android.os.Bundle;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.utils.SoundRecorder;
import org.drl.lutz.sosmehringplatzapp.main.utils.SoundRecorderWav;

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
    }

}
