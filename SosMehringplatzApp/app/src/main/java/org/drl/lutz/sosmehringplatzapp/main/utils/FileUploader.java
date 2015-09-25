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

        params.put("type",submission.type.toString());
        params.put("content_text",submission.text);
        if (submission.recording != null)
            params.put("content_recording", submission.recording);
        params.put("author_name",submission.author);
        if (submission.image != null)
            params.put("author_image", submission.image);

        httpClient.post(this.context,urlString,params,handler);
    }

    public void deleteFiles() {
        if (this.submission.recording != null)
            this.submission.recording.delete();
        if (this.submission.image != null)
            this.submission.image.delete();
    }

}