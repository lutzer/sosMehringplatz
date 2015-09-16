package org.drl.lutz.sosmehringplatzapp.main.utils;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by lutz on 13/04/15.
 */
public class FileUploader {

    private File file;

    private Context context;

    public FileUploader(Context context, File file) {
        this.file = file;
        this.context = context;
    }

    public void upload(final String urlString, AsyncHttpResponseHandler handler) throws FileNotFoundException {

        AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        try {
            params.put("file", this.file);
            //params.put("location","test");
        } catch(FileNotFoundException e) {
            throw e;
        }

        httpClient.post(this.context,urlString,params,handler);
    }

    public void deleteFile() {
        this.file.delete();
    }

}