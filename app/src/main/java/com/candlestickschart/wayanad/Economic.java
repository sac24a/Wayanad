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
import android.widget.Toast;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityEconomicBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Economic extends AppCompatActivity {

    ListView listView;
    String community = "";
    JSONObject jsonObject;
    EditText land;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic);
        ActivityEconomicBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_economic);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.listView);
        land = findViewById(R.id.otherCaste);

        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.ration));
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
        setData();
    }
    public void setData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(Economic.this);
                ArrayList<VoterListData> voterData = getIntent().getParcelableArrayListExtra("voterdata");
                for (int i = 0; i < voterData.size(); i++) {
                    List<VoterListData> voterDetails = pollFirstDataBase.pollFirstDao().getVoterDetails(voterData.get(i).Voter_ID);
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                            if (!voterDetails.get(0).Ration_card.equals("null")) {
                                String[] array = getResources().getStringArray(R.array.ration);
                                for (int i=0;i<array.length;i++) {
                                    if (array[i].equals(voterDetails.get(0).Ration_card)) {
                                        listView.setItemChecked(i,true);
                                        try {
                                            community = voterDetails.get(0).Ration_card;
                                            jsonObject.put("Ration_Card",community);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (!voterDetails.get(0).Land_holding.equals("null")) {
                                land.setText(voterDetails.get(0).Land_holding);
                            }

                        }
                    });
                }
            }
        });
    }

    public void backPressed(View view) {
        finish();
    }
    public void forwardPressed (View view){
        try {
            if (community.equals("")) {
                Toast.makeText(Economic.this,"Select Ration Type",Toast.LENGTH_SHORT).show();
                return;
            }
            if (land.getText().toString().equals("")) {
                Toast.makeText(Economic.this,"Enter Land Holding",Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("Ration_Card",community);
            jsonObject.put("Land_Holding",land.getText().toString());
            Intent intent = new Intent(this,Political.class);
            intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
            intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
            intent.putExtra("json",jsonObject.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}