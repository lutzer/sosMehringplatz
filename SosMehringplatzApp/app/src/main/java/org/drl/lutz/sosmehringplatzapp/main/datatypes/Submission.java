package org.drl.lutz.sosmehringplatzapp.main.datatypes;

import java.io.File;
import java.io.Serializable;

/**
 * Created by lutz on 16/09/15.
 */
public class Submission implements Serializable {

    public QuestionType type = null;
    public String text = null;
    public File recording = null;

    public String author = null;
    public File image = null;

    public Submission(QuestionType type) {
        this.type = type;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String text) {
        this.text = text;
    }

    public void setRecording(File file) {
        this.recording = file;
    }
}
