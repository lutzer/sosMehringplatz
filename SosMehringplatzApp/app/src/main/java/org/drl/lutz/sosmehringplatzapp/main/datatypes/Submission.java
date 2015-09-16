package org.drl.lutz.sosmehringplatzapp.main.datatypes;

import java.io.File;
import java.io.Serializable;

/**
 * Created by lutz on 16/09/15.
 */
public class Submission implements Serializable {

    public String type = null;
    public String author = null;
    public String text = null;
    public File file = null;
    public File image = null;

    public Submission(String type,String text) {
        this.type = type;
        this.text = text;
    }

    public Submission(String type,File file) {
        this.type = type;
        this.file = file;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
