package com.candlestickschart.wayanad;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class Webservice {

    String url;


    public void Webservice(String url){
        this.url =url;
    }


}
