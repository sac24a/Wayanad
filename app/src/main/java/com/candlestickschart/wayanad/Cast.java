package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.candlestickschart.wayanad.databinding.ActivityCastBinding;
import com.candlestickschart.wayanad.databinding.ActivityFamilyHeadBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cast extends AppCompatActivity {

    ListView listView;
    JSONObject jsonObject;
    String community = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);
        ActivityCastBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_cast);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.listView);
        String[] array ={};
        switch (getIntent().getStringExtra("community")) {
            case "Forward- Hindu":
                array = getResources().getStringArray(R.array.forwardCaste);
                break;
            case "OBCs":
                array = getResources().getStringArray(R.array.backwardCaste);
                break;
            case "Scheduled Castes":
                array = getResources().getStringArray(R.array.scCaste);
                break;
            case "Scheduled Tribes":
                array = getResources().getStringArray(R.array.stCaste);
                break;
            case "Christians":
                array = getResources().getStringArray(R.array.christianCaste);
                break;
            case "Muslims":
                array = getResources().getStringArray(R.array.muslimCaste);
                break;
            case "Others":
                array = getResources().getStringArray(R.array.minorityCaste);
                break;

        }

        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, array);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView v = (CheckedTextView) view;
                view.setActivated(true);
                community = ((CheckedTextView) view).getText().toString();

            }
        });
    }
    public void backPressed(View view) {
        finish();
    }
    public void forwardPressed (View view){
        try {
            jsonObject.put("Caste",community);
            Intent intent = new Intent(this,Economic.class);
            intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
            intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
            intent.putExtra("json",jsonObject.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}