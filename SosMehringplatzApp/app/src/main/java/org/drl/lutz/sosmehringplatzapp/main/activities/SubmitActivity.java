package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

public class SubmitActivity extends QuestionActivity {

    Submission submission = null;

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

    }
}
