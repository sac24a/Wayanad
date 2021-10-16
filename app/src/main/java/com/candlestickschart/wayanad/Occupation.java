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
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityOccupationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Occupation extends AppCompatActivity {

    ListView listView;
    String community = "";
    JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);

        ActivityOccupationBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_occupation);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);
        listView = findViewById(R.id.listView);
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.occupation));
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView v = (CheckedTextView) view;
                view.setActivated(true);
                community = ((CheckedTextView) view).getText().toString();
                try {
                    jsonObject.put("Voter_OCCU",community);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        setData();
    }
    public void setData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(Occupation.this);

                    List<VoterListData> voterDetails = pollFirstDataBase.pollFirstDao().getVoterDetails(getIntent().getStringExtra("EPIC_NO"));
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (!voterDetails.get(0).Occupation.equals("null")) {
                                String[] array = getResources().getStringArray(R.array.occupation);
                                for (int i=0;i<array.length;i++) {
                                    if (array[i].equals(voterDetails.get(0).Occupation)) {
                                        listView.setItemChecked(i,true);
                                        try {
                                            jsonObject.put("Voter_OCCU",voterDetails.get(0).Occupation);
                                            community = voterDetails.get(0).Occupation;
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    });
                }

        });
    }

    public void backPressed(View view) {
        finish();
    }
    public void forwardPressed (View view){

        if (community.equals("")) {
            Toast.makeText(Occupation.this,"Select Occupation",Toast.LENGTH_SHORT).show();
            return;
        }
//            jsonObject.put("Family_Head",community);
//            jsonObject.put("C_HOUSE_NO",getIntent().getStringExtra("C_HOUSE_NO"));
        Intent intent = new Intent(this,Community.class);
        intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
        intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
        intent.putExtra("EPIC_NO",getIntent().getStringExtra("EPIC_NO"));
        intent.putExtra("json",jsonObject.toString());
        startActivity(intent);

//        try {
//            if (!jsonObject.has("Voter_OCCU")) {
//                Toast.makeText(Occupation.this,"Select Occupation",Toast.LENGTH_SHORT).show();
//                return;
//            }
//            AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(Occupation.this);
//                        pollFirstDataBase.pollFirstDao().updateVoterIndividual(jsonObject.getString("Voter_Place"),jsonObject.getString("Current_Residence"),jsonObject.getString("Voter_DOB"),jsonObject.getString("Voter_Anniversary"),jsonObject.getString("Voter_Mobile"),jsonObject.getString("Voter_EDU"),jsonObject.getString("Voter_OCCU"),jsonObject.getString("EPIC_NO"));
//                        Intent intent = new Intent(Occupation.this, VoterDetails.class);
//                        intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
//                        intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        Occupation.this.finish();
//
//                    }
//                    catch (Exception e ) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        catch (NullPointerException e) {
//
//        }
    }
}