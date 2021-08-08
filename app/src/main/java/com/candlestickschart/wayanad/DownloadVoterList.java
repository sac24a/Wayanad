package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.candlestickschart.wayanad.databinding.ActivityDownloadVoterListBinding;
import com.candlestickschart.wayanad.databinding.ActivitySearchVoterBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadVoterList extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText boothno;
    Spinner vsNo;
    Button download;
    ProgressBar progressBar;
    ArrayList<VSData> vsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActivityDownloadVoterListBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_download_voter_list);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),"Booth No-"+sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),"");
        searchVoterBinding.setMainUser(user);

        boothno = findViewById(R.id.boothNo);
        download = findViewById(R.id.download);
        vsNo = findViewById(R.id.vsNo);
        progressBar = findViewById(R.id.progressBar);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!boothno.getText().toString().equals("")) {
                    if (!vsNo.getSelectedItem().toString().equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        editor.putString("booth_no",boothno.getText().toString());
                        for (int i = 0;i<vsData.size();i++) {
                            if (vsData.get(i).PartNo.equals(boothno.getText().toString())) {
                                editor.putString("booth_name",vsData.get(i).Booth_name);
                                editor.putString("booth_id",vsData.get(i).Booth_ID);
                                i = vsData.size();
                            }
                        }
                        editor.apply();
                        apicallToDownload();
                    }

                }
            }
        });
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(DownloadVoterList.this);
                    Log.d("Entered records", "onResponse: "+pollFirstDataBase.pollFirstDao().getVoterList().size());
                    if (pollFirstDataBase.pollFirstDao().getVoterList().size()!=0) {
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(DownloadVoterList.this,SearchVoter.class);
                                startActivity(intent);
                            }
                        });
                    }

                }
                catch (Exception e ) {

                }
            }
        });
        apicallToGetAcList();

    }

    public void setUpSpinner (ArrayList<String> acno) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(DownloadVoterList.this, android.R.layout.simple_spinner_item, acno);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vsNo.setAdapter(spinnerArrayAdapter);
        vsNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putString("acname",vsData.get(i).AcName);
                editor.putString("acno",vsData.get(i).AcNo);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void apicallToGetAcList(){
        try {
            String url = "http://ourwayanad.in/API/Assembly_ListRecord.php";
            Map<String, String> postParam= new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(postParam);
            Log.d("TAG", "apicallToLogin: "+ jsonObject);
            progressBar.setVisibility(View.VISIBLE);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Responselogin", response.toString());
                            try {
                                JSONObject jsonObject1 = response;
                                vsData = new ArrayList<>();
                                JSONArray jsonArray = jsonObject1.getJSONArray("Assmebly_list");
                                Log.d("TAG", "onResponse: "+jsonArray.length());
                                for (int i = 0;i<jsonArray.length();i++) {
                                    vsData.add(new VSData(jsonArray.getJSONObject(i).getString("ACNo"),jsonArray.getJSONObject(i).getString("ACNameEn"),jsonArray.getJSONObject(i).getString("Booth_ID"),jsonArray.getJSONObject(i).getString("PartNo"),jsonArray.getJSONObject(i).getString("Booth_name")));
                                }
                                ArrayList<String> acno = new ArrayList<>();
                                for (int i = 0;i<vsData.size();i++) {
                                    if (acno.size() ==0) {
                                        acno.add(vsData.get(i).AcNo);
                                    }
                                    else  {
                                        if (!acno.contains(vsData.get(i).AcNo)) {
                                            acno.add(vsData.get(i).AcNo);
                                        }
                                    }
                                }
                                setUpSpinner(acno);
                                progressBar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Its error === >", "onErrorResponse: "+error);
                            progressBar.setVisibility(View.GONE);

                        }
                    }

            ) ;
            Mysingleton.getInstance(getApplicationContext()).addToRequestque(postRequest);



        }
        catch (NullPointerException e) {

        }
    }

    public void apicallToDownload(){
        try {
            String url = "http://ourwayanad.in/API/Search_VoterListRecord.php";
            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("vs_no", vsNo.getSelectedItem().toString());
            postParam.put("Booth_no", boothno.getText().toString());

            JSONObject jsonObject = new JSONObject(postParam);
            Log.d("TAG", "apicallToLogin: "+ jsonObject);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // response
                            Log.e("Responselogin", response.toString());


                            try {
                                JSONObject jsonObject1 = response;
                                JSONArray jsonArray = jsonObject1.getJSONArray("VoterList");
                                Log.d("TAG", "onResponse: "+jsonArray.length());
                                for (int i = 0;i<jsonArray.length();i++){
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(DownloadVoterList.this);
                                                    VoterListData pollFirstData = new VoterListData(
                                                            jsonObject.getString("State"),
                                                            jsonObject.getString("LS_No"),
                                                            jsonObject.getString("ACNo"),
                                                            jsonObject.getString("ACNameEn"),
                                                            jsonObject.getString("ACName"),
                                                            jsonObject.getString("Booth_ID"),
                                                            jsonObject.getString("PartNo"),
                                                            jsonObject.getString("SectionNo"),
                                                            jsonObject.getString("SectionNameEn"),
                                                            jsonObject.getString("Fam_ID"),
                                                            jsonObject.getString("Voter_ID"),
                                                            jsonObject.getString("SNo"),
                                                            jsonObject.getString("HouseNoEn"),
                                                            jsonObject.getString("HouseNo"),
                                                            jsonObject.getString("Voter_name"),
                                                            jsonObject.getString("Voter_name_MAL"),
                                                            jsonObject.getString("RelationType"),
                                                            jsonObject.getString("Rel_name"),
                                                            jsonObject.getString("Rel_name_MAL"),
                                                            jsonObject.getString("VoterID"),
                                                            jsonObject.getString("Sex"),
                                                            jsonObject.getString("Age"),
                                                            jsonObject.getString("ContactNo"),
                                                            jsonObject.getString("HOF"),
                                                            jsonObject.getString("Segment"),
                                                            jsonObject.getString("Community"),
                                                            jsonObject.getString("Ration_card"),
                                                            jsonObject.getString("Land_holding"),
                                                            jsonObject.getString("Pol_affinity"),
                                                            jsonObject.getString("Livelihood"),
                                                            jsonObject.getString("Live_Here"),
                                                            jsonObject.getString("Alt_address"),
                                                            jsonObject.getString("DOB"),
                                                            jsonObject.getString("Anniversary"),
                                                            jsonObject.getString("Whatsapp_no"),
                                                            jsonObject.getString("Education"),
                                                            jsonObject.getString("Occupation"),"");
                                                    pollFirstDataBase.pollFirstDao().insertVoterList(pollFirstData);

                                                }
                                                catch (Exception e ) {

                                                }
                                            }
                                        });
                                    }
                                    catch (NullPointerException e) {

                                    }
                                }
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(DownloadVoterList.this);
                                            Log.d("Entered records", "onResponse: "+pollFirstDataBase.pollFirstDao().getVoterList().size());
                                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBar.setVisibility(View.GONE);
                                                    Intent intent = new Intent(DownloadVoterList.this,SearchVoter.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                        catch (Exception e ) {

                                        }
                                    }
                                });



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Its error === >", "onErrorResponse: "+error);
                            progressBar.setVisibility(View.GONE);

                        }
                    }

            ) ;
            Mysingleton.getInstance(getApplicationContext()).addToRequestque(postRequest);



        }
        catch (NullPointerException e) {

        }
    }
}