package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

public class TextActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));
    }

    public void onAcceptButtonClicked(View view) {

        Intent intent = new Intent(getBaseContext(), UploadActivity.class);
        intent.putExtra("submission",new Submission("text","test text"));

        startActivity(intent);
        finish();
    }
}
