package org.drl.lutz.sosmehringplatzapp.main.activities;

import android.view.View;

import org.drl.lutz.sosmehringplatzapp.R;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.QuestionType;
import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

/**
 * Created by lutz on 17/09/15.
 */
public abstract class QuestionActivity extends FullscreenActivity {

    public void setQuestionType(QuestionType type) {
        // set background color according to category
        View mainLayout = getWindow().getDecorView().findViewById(android.R.id.content);
        switch (type) {
            case QUESTION:
                mainLayout.setBackgroundResource(R.color.bgColorQuestion);
                break;
            case RANT:
                mainLayout.setBackgroundResource(R.color.bgColorRant);
                break;
            case IDEA:
                mainLayout.setBackgroundResource(R.color.bgColorIdea);
                break;
        }
    }
}
