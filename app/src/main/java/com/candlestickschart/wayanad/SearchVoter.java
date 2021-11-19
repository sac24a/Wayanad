package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.candlestickschart.wayanad.databinding.ActivitySearchVoterBinding;
import com.candlestickschart.wayanad.databinding.HeaderBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchVoter extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText searchText;
    Button searchButton;
    Button uploadButton;
    Button logoutButton;
    Button byName;
    Button byNumber;
    ProgressBar progressBar;
    SharedPreferences.Editor editor;
    int buttonid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActivitySearchVoterBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_voter);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),"");
        searchVoterBinding.setMainUser(user);

        searchButton = findViewById(R.id.login);
        uploadButton = findViewById(R.id.upload);
        logoutButton = findViewById(R.id.logout);
        searchText = findViewById(R.id.searchString);
        progressBar = findViewById(R.id.progressBar);
        byName = findViewById(R.id.byName);
        byNumber = findViewById(R.id.byNumber);

        byName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byName.setBackgroundResource(R.drawable.newsample);
                byNumber.setBackgroundResource(R.drawable.transparent);
                byNumber.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                byName.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                buttonid = 0;
            }
        });
        byNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byName.setBackgroundResource(R.drawable.transparent);
                byName.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                byNumber.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                byNumber.setBackgroundResource(R.drawable.newsample);
                buttonid = 1;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonid == 0) {
                    editor.putString("search",searchText.getText().toString());
                    getDetails(searchText.getText().toString());
                }
                else if (buttonid ==1) {
                    editor.putString("search",searchText.getText().toString());
                    getDetailsByNumber(searchText.getText().toString());
                }

            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                setUpUpload();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear().apply();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                        pollFirstDataBase.clearAllTables();
                        if (pollFirstDataBase.pollFirstDao().getVoterList().size()==0) {
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SearchVoter.this,LoginScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }

                    }
                });

            }
        });

    }

    public void getDetailsByNumber(String value) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                String houseNo = pollFirstDataBase.pollFirstDao().searchVoterList(value);
                List<VoterListData> familyList = pollFirstDataBase.pollFirstDao().searchVoterFamilyList(houseNo);
                ArrayList<String> name = new ArrayList<>();
                for (int i =0;i<familyList.size();i++) {
                    name.add(familyList.get(i).SNo+"."+familyList.get(i).Voter_name+" - "+familyList.get(i).Sex+" ("+familyList.get(i).Age+")"+" - "+familyList.get(i).HouseNoEn);
                }
                Log.d("TAG", "run: "+familyList.size());
                Intent intent = new Intent(SearchVoter.this,VoterDetails.class);
                intent.putParcelableArrayListExtra("voterdata", (ArrayList<? extends Parcelable>) familyList);
                intent.putStringArrayListExtra("voterlist", name);
                startActivity(intent);
            }
        });
    }

    public void getDetails(String value) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                List<VoterListData> voterListDataList= pollFirstDataBase.pollFirstDao().searchVoterListByName(value);
//                List<VoterListData> familyList = pollFirstDataBase.pollFirstDao().searchVoterFamilyList(houseNo);
                ArrayList<String> name = new ArrayList<>();
                for (int i =0;i<voterListDataList.size();i++) {
                    name.add(voterListDataList.get(i).SNo+"."+voterListDataList.get(i).Voter_name+" - "+voterListDataList.get(i).Sex+" ("+voterListDataList.get(i).Age+")"+" - "+voterListDataList.get(i).HouseNoEn);
                }
                Intent intent = new Intent(SearchVoter.this,SearchByNameList.class);
                intent.putParcelableArrayListExtra("voterdata", (ArrayList<? extends Parcelable>) voterListDataList);
                intent.putStringArrayListExtra("voterlist", name);
                startActivity(intent);
            }
        });
    }

    public void setUpUpload() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                List<VoterListData> pollFirstData = pollFirstDataBase.pollFirstDao().getCompletedVoterDetails("completed");
                List<NewVoterData> socialData = pollFirstDataBase.pollFirstDao().getNewVoterData();

                JSONArray socialArray = new JSONArray();
                JSONArray politicalArray = new JSONArray();
                JSONObject jsonToUpload = new JSONObject();

                try {
                    for (int i = 0;i<socialData.size();i++) {
                        JSONObject socialJson = new JSONObject();
                        socialJson.put("user_id",sharedPreferences.getString("user_id",""));
                        socialJson.put("vs_no",sharedPreferences.getString("vs_no",""));
                        socialJson.put("vs_name",sharedPreferences.getString("vs_name",""));
                        socialJson.put("user_mobile_no",sharedPreferences.getString("user_mobile_no",""));
                        socialJson.put("Fam_ID",socialData.get(i).C_HOUSE_NO);
                        socialJson.put("New_Name",socialData.get(i).New_Name);
                        socialJson.put("New_Gender",socialData.get(i).New_Gender);
                        socialJson.put("New_DOB",socialData.get(i).New_DOB);
                        socialJson.put("New_Mobile",socialData.get(i).New_Mobile);
                        socialJson.put("famgrp_id",socialData.get(i).famgrp_id);
                        socialArray.put(socialJson);
                    }
                    for (int i = 0;i<pollFirstData.size();i++) {
                        JSONObject politicalJson = new JSONObject();
                        politicalJson.put("user_id",sharedPreferences.getString("user_id",""));
                        politicalJson.put("vs_no",sharedPreferences.getString("vs_no",""));
                        politicalJson.put("vs_name",sharedPreferences.getString("vs_name",""));
                        politicalJson.put("user_mobile_no",sharedPreferences.getString("user_mobile_no",""));
                        politicalJson.put("State",pollFirstData.get(i).State);
                        politicalJson.put("LS_No",pollFirstData.get(i).LS_No);
                        politicalJson.put("ACNo",pollFirstData.get(i).ACNo);
                        politicalJson.put("ACNameEn",pollFirstData.get(i).ACNameEn);
                        politicalJson.put("ACName",pollFirstData.get(i).ACName);
                        politicalJson.put("Booth_ID",pollFirstData.get(i).Booth_ID);
                        politicalJson.put("PartNo",pollFirstData.get(i).PartNo);
                        politicalJson.put("SectionNo",pollFirstData.get(i).SectionNo);
                        politicalJson.put("SectionNameEn",pollFirstData.get(i).SectionNameEn);
                        politicalJson.put("Fam_ID",pollFirstData.get(i).Fam_ID);
                        politicalJson.put("Voter_ID",pollFirstData.get(i).Voter_ID);
                        politicalJson.put("SNo",pollFirstData.get(i).SNo);
                        politicalJson.put("HouseNoEn",pollFirstData.get(i).HouseNoEn);
                        politicalJson.put("HouseNo",pollFirstData.get(i).HouseNo);
                        politicalJson.put("Voter_name",pollFirstData.get(i).Voter_name);
                        politicalJson.put("Voter_name_MAL",pollFirstData.get(i).Voter_name_MAL);
                        politicalJson.put("RelationType",pollFirstData.get(i).RelationType);
                        politicalJson.put("Rel_name",pollFirstData.get(i).Rel_name);
                        politicalJson.put("Rel_name_MAL",pollFirstData.get(i).Rel_name_MAL);
                        politicalJson.put("VoterID",pollFirstData.get(i).VoterID);
                        politicalJson.put("Sex",pollFirstData.get(i).Sex);
                        politicalJson.put("Age",pollFirstData.get(i).Age);
                        politicalJson.put("ContactNo",pollFirstData.get(i).ContactNo);
                        politicalJson.put("HOF","");
                        politicalJson.put("Segment",pollFirstData.get(i).Segment);
                        politicalJson.put("Community",pollFirstData.get(i).Community);
                        politicalJson.put("Ration_card",pollFirstData.get(i).Ration_card);
                        politicalJson.put("Land_holding",pollFirstData.get(i).Land_holding);
                        politicalJson.put("Pol_affinity",pollFirstData.get(i).Pol_affinity);
                        politicalJson.put("Livelihood",pollFirstData.get(i).Livelihood);
                        politicalJson.put("Live_Here",pollFirstData.get(i).Live_Here);
                        politicalJson.put("Alt_address",pollFirstData.get(i).Alt_address);
                        politicalJson.put("DOB",pollFirstData.get(i).DOB);
                        politicalJson.put("Anniversary",pollFirstData.get(i).Anniversary);
                        politicalJson.put("Whatsapp_no",pollFirstData.get(i).Whatsapp_no);
                        politicalJson.put("Education",pollFirstData.get(i).Education);
                        politicalJson.put("Occupation",pollFirstData.get(i).Occupation);
                        politicalJson.put("Status",pollFirstData.get(i).Status);
                        politicalJson.put("Vehicle",pollFirstData.get(i).Vehicle);
                        politicalJson.put("famgrp_id",pollFirstData.get(i).famgrp_id);
                        politicalArray.put(politicalJson);
                    }

                    jsonToUpload.put("voter_detail",politicalArray);
                    jsonToUpload.put("new_voter",socialArray);
                    Log.d("TAG", "run: "+jsonToUpload);
                    sendData(jsonToUpload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendData(JSONObject jsonObject) throws JSONException {
        String url = "http://ourwayanad.in/API/Insert_Record.php";

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.e("Responselogin", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {

                            JSONObject jsonObject = new JSONObject(response.toString());
                            if (jsonObject.getBoolean("success"))
                            {
                                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(SearchVoter.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            else  {
                                Toast.makeText(SearchVoter.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {

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

}