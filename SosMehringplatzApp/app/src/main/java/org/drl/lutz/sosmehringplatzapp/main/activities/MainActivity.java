package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;


public class MainActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        this.transitionNext();
    }

    public void transitionNext() {
        startActivity(new Intent(this, SelectInputActivity.class));
    }
}
