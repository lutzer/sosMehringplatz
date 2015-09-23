package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;

public class TextActivity extends QuestionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        setIdleCloseTimer(getResources().getInteger(R.integer.idleTime));

        QuestionType type = (QuestionType)getIntent().getSerializableExtra("type");
        this.setQuestionType(type);

        EditText messageField = (EditText) findViewById(R.id.editText);
        messageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onUserInteraction();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void onAcceptButtonClicked(View view) {
        EditText messageField = (EditText) findViewById(R.id.editText);

        Intent intent = new Intent();
        intent.putExtra("message",messageField.getText().toString());

        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void onCancelButtonClicked(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
