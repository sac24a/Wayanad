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

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityFamilyHeadBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Community extends AppCompatActivity {

    ListView listView;
    JSONObject jsonObject;
    String community = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCommunityBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_community);
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
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.community));
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
            jsonObject.put("Social_Group",community);
            Intent intent = new Intent(this,Cast.class);
            intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
            intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
            intent.putExtra("json",jsonObject.toString());
            intent.putExtra("community",community);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}