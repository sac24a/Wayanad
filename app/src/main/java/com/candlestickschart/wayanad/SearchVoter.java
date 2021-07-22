package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    ProgressBar progressBar;

    ArrayList<VoterData> voterData;
    ArrayList<String> voterName;
    SharedPreferences.Editor editor;
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
        searchText = findViewById(R.id.searchString);
        progressBar = findViewById(R.id.progressBar);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                editor.putString("search",searchText.getText().toString());
                apicallToGetSearchResult();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                setUpUpload();
            }
        });

    }

    public void apicallToGetSearchResult() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String URL = "https://linier.in/wayanad/API/Search_VoterListRecord.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                voterData = new ArrayList<>();
                voterName = new ArrayList<>();
                progressBar.setVisibility(View.GONE);
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            voterData.add(new VoterData(jsonObject.getString("id"),jsonObject.getString("AC_NO"),jsonObject.getString("PART_NO"),jsonObject.getString("SECTION_NO"),jsonObject.getString("SLNOINPART"),jsonObject.getString("C_HOUSE_NO"),jsonObject.getString("C_HOUSE_NO_V1"),jsonObject.getString("FM_NAME_EN"),jsonObject.getString("LASTNAME_EN"),jsonObject.getString("FM_NAME_V1"),jsonObject.getString("LASTNAME_V1"),jsonObject.getString("RLN_TYPE"),jsonObject.getString("RLN_FM_NM_EN"),jsonObject.getString("RLN_L_NM_EN"),jsonObject.getString("RLN_FM_NM_V1"),jsonObject.getString("RLN_L_NM_V1"),jsonObject.getString("EPIC_NO"),jsonObject.getString("GENDER"),jsonObject.getString("AGE"),jsonObject.getString("DOB"),jsonObject.getString("MOBILE_NO")));
                            voterName.add(jsonObject.getString("FM_NAME_EN"));
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        PollFirstData pollFirstData = new PollFirstData(jsonObject.getString("AC_NO"),jsonObject.getString("PART_NO"),jsonObject.getString("SECTION_NO"),jsonObject.getString("SLNOINPART"),jsonObject.getString("C_HOUSE_NO"),jsonObject.getString("C_HOUSE_NO_V1"),jsonObject.getString("FM_NAME_EN"),jsonObject.getString("LASTNAME_EN"),jsonObject.getString("FM_NAME_V1"),jsonObject.getString("LASTNAME_V1"),jsonObject.getString("RLN_TYPE"),jsonObject.getString("RLN_FM_NM_EN"),jsonObject.getString("RLN_L_NM_EN"),jsonObject.getString("RLN_FM_NM_V1"),jsonObject.getString("RLN_L_NM_V1"),jsonObject.getString("EPIC_NO"),jsonObject.getString("GENDER"),jsonObject.getString("AGE"),jsonObject.getString("DOB"),jsonObject.getString("MOBILE_NO"),"","","","","","","","","","","","","","","","","","","");
                                        List<String> name = pollFirstDataBase.pollFirstDao().checkPollfirstData(jsonObject.getString("EPIC_NO"));
                                        if (name.size() == 0){
                                            pollFirstDataBase.pollFirstDao().insertPollFirst(pollFirstData);
                                        }
                                    }
                                    catch (Exception e ) {

                                    }
                                }
                            });
                        }
                        catch (NullPointerException e) {

                        }

                    }
                    Intent intent = new Intent(SearchVoter.this,VoterDetails.class);
                    intent.putExtra("voterlist",voterName);
                    intent.putExtra("voterdata",voterData);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                progressBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("textvalue",searchText.getText().toString());
                return params;
            }

        };

        requestQueue.add(stringRequest);
    }

    public void setUpUpload() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                List<PollFirstData> pollFirstData = pollFirstDataBase.pollFirstDao().getPollfirstData();
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
                        socialJson.put("ls_no",sharedPreferences.getString("ls_no",""));
                        socialJson.put("ls_name",sharedPreferences.getString("ls_name",""));
                        socialJson.put("user_mobile_no",sharedPreferences.getString("user_mobile_no",""));
                        socialJson.put("booth_no",sharedPreferences.getString("booth_no",""));
                        socialJson.put("booth_name",sharedPreferences.getString("booth_name",""));
                        socialJson.put("C_HOUSE_NO",socialData.get(i).C_HOUSE_NO);
                        socialJson.put("New_Name",socialData.get(i).New_Name);
                        socialJson.put("New_Gender",socialData.get(i).New_Gender);
                        socialJson.put("New_DOB",socialData.get(i).New_DOB);
                        socialJson.put("New_Mobile",socialData.get(i).New_Mobile);
                        socialArray.put(socialJson);
                    }
                    for (int i = 0;i<pollFirstData.size();i++) {
                        JSONObject politicalJson = new JSONObject();
                        politicalJson.put("user_id",sharedPreferences.getString("user_id",""));
                        politicalJson.put("vs_no",sharedPreferences.getString("vs_no",""));
                        politicalJson.put("vs_name",sharedPreferences.getString("vs_name",""));
                        politicalJson.put("ls_no",sharedPreferences.getString("ls_no",""));
                        politicalJson.put("ls_name",sharedPreferences.getString("ls_name",""));
                        politicalJson.put("user_mobile_no",sharedPreferences.getString("user_mobile_no",""));
                        politicalJson.put("booth_no",sharedPreferences.getString("booth_no",""));
                        politicalJson.put("booth_name",sharedPreferences.getString("booth_name",""));

                        politicalJson.put("C_HOUSE_NO",pollFirstData.get(i).C_HOUSE_NO);
                        politicalJson.put("EPIC_NO",pollFirstData.get(i).EPIC_NO);
                        politicalJson.put("Family_Head",pollFirstData.get(i).Family_Head);
                        politicalJson.put("Social_Group",pollFirstData.get(i).Social_Group);
                        politicalJson.put("Caste",pollFirstData.get(i).Caste);
                        politicalJson.put("Ration_Card",pollFirstData.get(i).Ration_Card);
                        politicalJson.put("Land_Holding",pollFirstData.get(i).Land_Holding);
                        politicalJson.put("Political_Affinity",pollFirstData.get(i).Political_Affinity);
                        politicalJson.put("Source_Livelihood",pollFirstData.get(i).Source_Livelihood);

                        politicalJson.put("Voter_Place",pollFirstData.get(i).Voter_Place);
                        politicalJson.put("Current_Residence",pollFirstData.get(i).Current_Residence);
                        politicalJson.put("Voter_DOB",pollFirstData.get(i).Voter_DOB);
                        politicalJson.put("Voter_Anniversary",pollFirstData.get(i).Voter_Anniversary);
                        politicalJson.put("Voter_Mobile",pollFirstData.get(i).Voter_Mobile);
                        politicalJson.put("Voter_Whatsapp",pollFirstData.get(i).Voter_Whatsapp);
                        politicalJson.put("Voter_EDU",pollFirstData.get(i).Voter_EDU);
                        politicalJson.put("Voter_OCCU",pollFirstData.get(i).Voter_OCCU);
                        politicalArray.put(politicalJson);
                    }

                    jsonToUpload.put("voter_detail",politicalArray);
                    jsonToUpload.put("new_voter",socialArray);
                    sendData(jsonToUpload);
                    Log.d("JsontoUpload", "run: "+jsonToUpload);
                    Log.d("Social", "run: "+socialArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendData(JSONObject jsonObject) throws JSONException {
        String url = "https://linier.in/wayanad/API/Insert_Record.php";

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
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchVoter.this);
                                        pollFirstDataBase.pollFirstDao().clearNewVoterTable();
                                        pollFirstDataBase.pollFirstDao().clearPollfirstTable();
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