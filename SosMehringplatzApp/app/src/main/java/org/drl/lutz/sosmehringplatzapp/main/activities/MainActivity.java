package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;


public class MainActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        if (view == findViewById(R.id.questionButton))
            this.transitionNext(QuestionType.QUESTION);
        else if (view == findViewById(R.id.rantButton))
            this.transitionNext(QuestionType.RANT);
        else
            this.transitionNext(QuestionType.IDEA);
    }

    public void transitionNext(QuestionType type) {
        Intent intent = new Intent(this, SelectInputActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
