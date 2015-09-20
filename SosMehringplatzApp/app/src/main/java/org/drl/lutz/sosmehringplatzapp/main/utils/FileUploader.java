package org.drl.lutz.sosmehringplatzapp.main.utils;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.drl.lutz.sosmehringplatzapp.main.datatypes.Submission;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by lutz on 13/04/15.
 */
public class FileUploader {

    private Submission submission;

    private Context context;

    public FileUploader(Context context, Submission submission) {
        this.submission = submission;
        this.context = context;
    }

    public void upload(final String urlString, AsyncHttpResponseHandler handler) throws FileNotFoundException {

        AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            params.put("file", submission.file);
            params.put("submission_type",submission.type.toString());
            params.put("submission_author",submission.author);
            params.put("submission_message",submission.text);
        } catch(FileNotFoundException e) {
            throw e;
        }

        httpClient.post(this.context,urlString,params,handler);
    }

    public void deleteFile() {
        this.submission.file.delete();
    }

}