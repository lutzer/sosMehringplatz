package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;
import org.drl.lutz.sosmehringplatzapp.main.utils.FileUploader;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;


public class UploadActivity extends QuestionActivity {

    View flyingBall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //receive data from previous activity
        Submission submission = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            submission = (Submission)extras.get("submission");
        } else {
            showAlert("Error", "No submission received from activity");
            return;
        }

        flyingBall = findViewById(R.id.flyingBall);

        if (submission.type == QuestionType.IDEA)
            flyingBall.setBackground(getResources().getDrawable(R.drawable.idea_dot));
        else if (submission.type == QuestionType.QUESTION)
            flyingBall.setBackground(getResources().getDrawable(R.drawable.question_dot));
        else
            flyingBall.setBackground(getResources().getDrawable(R.drawable.rant_dot));

        //start upload to web server automatically
        uploadSubmission(submission);

    }

    public void uploadSubmission(Submission submission) {

        final FileUploader uploader = new FileUploader(getApplicationContext(),submission);

        String uploadUrl = getResources().getString(R.string.UploadUrl);
        //String uploadUrl = "http://192.168.1.12:3000/api/submissions/";

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

        try {
            uploader.upload(uploadUrl,  new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    ((TextView)findViewById(R.id.bodyText)).setText(R.string.uploadBodyTextFinished);
                    progressBar.setProgress(100);

                    final Animation ballAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flying_ball);
                    flyingBall.startAnimation(ballAnimation);

                    done();

                    uploader.deleteFiles();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    throwable.printStackTrace();
                    showAlert("Error", "Failed to upload file to server: " + throwable.toString());

                    done();
                }

                @Override
                public void onProgress(int position, int length) {
                    progressBar.setProgress((int) (position / (float) length * 100));
                    Log.e("UPLOAD","progress: "+position+":"+length);
                }
            });


        } catch (Exception e) {
            this.showAlert("Error","Error uploading submission: "+e.toString());
            return;
        }
    }

    public void done() {

        //wait for 2,5 seconds, then close activity
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TIMER", "finish activity");
                finish();
            }
        }, 4000);

    }
}
