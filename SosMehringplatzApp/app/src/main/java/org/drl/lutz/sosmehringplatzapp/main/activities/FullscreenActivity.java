package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;
import org.drl.lutz.sosmehringplatzapp.main.utils.IdleTimer;

/**
 * Created by lutz on 10/04/15.
 */
abstract public class FullscreenActivity extends Activity {


    private final Handler mHideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideSystemUI();
        }
    };
    private boolean immersiveModeOn = false;

    //idle timer closes activity
    private IdleTimer idleTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide all system elements
        //setImmersiveMode(true);

        // never turn screen off
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (idleTimer != null)
            idleTimer.stopTimer();
    }

    public void setImmersiveMode(boolean on) {
        // AUTO HIDE ALL SYSTEM UI ELEMENTS
        immersiveModeOn = on;
        if (on) {
            hideSystemUI();
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    hideSystemUI();
                }
            });
        } else {
            showSystemUI();;
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
        }
    }

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeMessages(0);
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    //closes activity after beeing idle for too long
    public void setIdleCloseTimer(int idleTime) {
        idleTimer=new IdleTimer(idleTime,new IdleTimer.IdleTimerEventListener() {
            @Override
            public void onIdleTooLong() {
                //close activity
                finish();
            }
        });
        idleTimer.start();
    }

    public void pauseIdleTimer(boolean pause) {
        if (pause)
            this.idleTimer.pause();
        else
            this.idleTimer.unpause();
    }

    @Override
    public void onUserInteraction() {
        if (idleTimer != null) {
            idleTimer.reset();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!immersiveModeOn)
            return;
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        if (hasFocus) {
            delayedHide(300);
        } else {
            mHideHandler.removeMessages(0);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        //overridePendingTransition(R.anim.trans_in_back, R.anim.trans_out_back);
    }

    public void showAlert(final String title,final String message) {
        if (this.isFinishing())
            return;

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialog.show();
    }
}
