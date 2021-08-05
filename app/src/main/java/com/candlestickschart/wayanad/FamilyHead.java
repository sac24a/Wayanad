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
import android.widget.Toast;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityFamilyHeadBinding;
import com.candlestickschart.wayanad.databinding.ActivitySearchVoterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FamilyHead extends AppCompatActivity {

    ListView listView;
    JSONObject jsonObject;
    String community = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFamilyHeadBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_family_head);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        jsonObject = new JSONObject();
        listView = findViewById(R.id.listView);
        ArrayList<String > arrayList = getIntent().getStringArrayListExtra("voterlist");
        ArrayList<VoterData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, arrayList);
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
        setData(arrayList);
    }
    public void setData(ArrayList<String>array) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(FamilyHead.this);
                ArrayList<VoterListData> voterData = getIntent().getParcelableArrayListExtra("voterdata");
                for (int i = 0; i < voterData.size(); i++) {
                    List<VoterListData> voterDetails = pollFirstDataBase.pollFirstDao().getVoterDetails(voterData.get(i).Voter_ID);
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {

                            if (!voterDetails.get(0).HOF.equals("null")) {
                                for (int i=0;i<array.size();i++) {
                                    if (array.get(i).equals(voterDetails.get(0).HOF)) {
                                        listView.setItemChecked(i,true);
                                        try {
                                            community = voterDetails.get(0).HOF;
                                            jsonObject.put("Family_Head",community);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
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
                Toast.makeText(FamilyHead.this,"Select Family Head",Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("Family_Head",community);
            jsonObject.put("C_HOUSE_NO",getIntent().getStringExtra("C_HOUSE_NO"));
            Intent intent = new Intent(this,Community.class);
            intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
            intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
            intent.putExtra("json",jsonObject.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}