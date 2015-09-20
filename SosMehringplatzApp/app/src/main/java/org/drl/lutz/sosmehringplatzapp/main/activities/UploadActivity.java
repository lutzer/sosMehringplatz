package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;
import org.drl.lutz.sosmehringplatzapp.main.utils.FileUploader;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;


public class UploadActivity extends QuestionActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        //receive data from previous activity
        Submission submission = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            submission = (Submission)extras.get("submission");
        } else {
            showAlert("Error", "No submission made");
            return;
        }

        this.setQuestionType(submission.type);

        //start upload to web server automatically
        uploadSubmission(submission);

    }

    public void uploadSubmission(Submission submission) {

        final FileUploader uploader = new FileUploader(getApplicationContext(),submission);

        String uploadUrl = getResources().getString(R.string.UploadWebUrl);

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

        try {
            uploader.upload(uploadUrl,  new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    ((TextView)findViewById(R.id.bodyText)).setText(R.string.uploadBodyTextFinished);
                    findViewById(R.id.acceptButton).setEnabled(true);
                    progressBar.setProgress(100);
                    pauseIdleTimer(false);

                    done();

                    //delete file after succesfull upload
                    //uploader.deleteFile();
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
                }
            });

            pauseIdleTimer(true);

        } catch (Exception e) {
            this.showAlert("Error","Error uploading submission: "+e.toString());
            return;
        }
    }

    public void done() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TIMER", "finish activity");
                finish();
            }
        }, 2000);

    }
}
