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
import android.widget.EditText;
import android.widget.ListView;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityLivelyhoodBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Livelyhood extends AppCompatActivity {

    ListView listView;
    String community = "";
    JSONObject jsonObject;
    EditText editText;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livelyhood);

        ActivityLivelyhoodBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_livelyhood);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.otherCaste);

        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.livelihood));
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
            try {
                jsonObject.put("Source_Livelihood",community);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(Livelyhood.this);
                        pollFirstDataBase.pollFirstDao().updateVoterDetail(jsonObject.getString("Family_Head"),jsonObject.getString("Social_Group"),jsonObject.getString("Caste"),jsonObject.getString("Ration_Card"),jsonObject.getString("Land_Holding"),jsonObject.getString("Political_Affinity"),jsonObject.getString("Source_Livelihood"),jsonObject.getString("C_HOUSE_NO"));
                        Intent intent = new Intent(Livelyhood.this, VoterDetails.class);
                        intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
                        intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Livelyhood.this.finish();

                    }
                    catch (Exception e ) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}