package com.retailshop.retailshopapplication.model.repo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;


/**
 * Created by sreedhar on 1/15/18.
 */

public class OnlineRepo implements IRepo {

    private static final String BASE_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1";
    private static final String apiKey = "3e2f38d0-d26c-416e-b028-1d5e0f8350ed";
    public static final String FETCH_URL = "/walmartproducts";
    final String TAG = "OnlineRepo";

    IRepo.ICallbacks mCallback;
    
    RetailProductResponse retailProductsResponse;


    public void register(ICallbacks callbacks) {
        mCallback = callbacks;
    }

    @Override
    public void unregister() {

    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public void fetchProducts(final int pageNo, final int pageSize) {
        new AsyncTask<Void, String, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(BASE_URL+FETCH_URL+"/"+apiKey+"/"+pageNo+"/"+pageSize)
                            .build();
                    Log.d(TAG, "request "+request.url());

                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        Log.d(TAG, "result "+request.url());
                        String body = response.body().string();

                        retailProductsResponse = parseJSon(body);
                        return "success";
                    }
                    else {
                        return "error in network"+response.message();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "error";
            }

            @Override
            public void onPostExecute(String result) {
                Log.d(TAG, " response "+result);
                if (result.equals("success")) {
                    if (mCallback != null) {
                        mCallback.onFinished(retailProductsResponse);
                    }
                }
                else {
                    if (mCallback != null) {
                        mCallback.onFailuer(result);
                    }
                }
            }
        }.execute();
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private RetailProductResponse parseJSon(String body) {
        Log.d(TAG, "rsponse "+body);
        Gson gson = new Gson();
        Type ProductList = new TypeToken<RetailProductResponse>() {

        }.getType();

        RetailProductResponse response = gson.fromJson(body, RetailProductResponse.class);
//        try {
//            JSONObject jsonObject = new JSONObject(body);
//            String str = jsonObject.getJSONArray("products").toString();
//            response.products = gson.fromJson(str, ProductList);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return response;
    }
}
