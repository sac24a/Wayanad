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

import com.candlestickschart.wayanad.databinding.ActivityCastBinding;
import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityEducationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Education extends AppCompatActivity {

    ListView listView;
    String community = "";

    JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        ActivityEducationBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_education);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.listView);

        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.education));
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                view.setActivated(true);
                community = ((CheckedTextView) view).getText().toString();

            }
        });
        setData();

    }
    public void setData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(Education.this);
                    List<VoterListData> voterDetails = pollFirstDataBase.pollFirstDao().getVoterDetails(getIntent().getStringExtra("EPIC_NO"));
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                            if (!voterDetails.get(0).Education.equals("null")) {
                                String[] array = getResources().getStringArray(R.array.education);
                                for (int i=0;i<array.length;i++) {
                                    if (array[i].equals(voterDetails.get(0).Education)) {
                                        listView.setItemChecked(i,true);
                                        try {
                                            community = voterDetails.get(0).Education;
                                            jsonObject.put("Voter_EDU",voterDetails.get(0).Education);

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
            Toast.makeText(Education.this,"Select Education",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            jsonObject.put("Voter_EDU",community);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this,Occupation.class);
        intent.putExtra("json",jsonObject.toString());
        intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
        intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
        intent.putExtra("EPIC_NO",getIntent().getStringExtra("EPIC_NO"));
        startActivity(intent);
    }
}