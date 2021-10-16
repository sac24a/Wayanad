package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.candlestickschart.wayanad.databinding.ActivitySearchByNameListBinding;
import com.candlestickschart.wayanad.databinding.ActivityVoterDetailsBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchByNameList extends AppCompatActivity {
    ListView listView;
    Button backButotn;
    JSONObject jsonObject;
    SharedPreferences sharedPreferences;
    String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        ActivitySearchByNameListBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_by_name_list);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);


        listView = findViewById(R.id.listView);
        backButotn = findViewById(R.id.backward);
        backButotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jsonObject = new JSONObject();

        ArrayList<String > arrayList = getIntent().getStringArrayListExtra("voterlist");
        ArrayList<VoterListData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, arrayList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getDetails(voterData.get(i).Fam_ID);
                Log.d("TAG", "onItemClick: "+voterData.get(i).Fam_ID);
//                Intent intent = new Intent(SearchByNameList.this,IndividualDetail.class);
//                intent.putExtra("voterdata",voterData);
//                intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
//                intent.putExtra("EPIC_NO",voterData.get(i).Voter_ID);
//                intent.putExtra("voterdetails",arrayList.get(i));
//                startActivity(intent);
            }
        });
    }
    public void getDetails(String value) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchByNameList.this);
                List<VoterListData> familyList = pollFirstDataBase.pollFirstDao().searchVoterFamilyList(value);
                ArrayList<String> name = new ArrayList<>();
                for (int i =0;i<familyList.size();i++) {
                    name.add(familyList.get(i).SNo+"."+familyList.get(i).Voter_name+" - "+familyList.get(i).Sex+" ("+familyList.get(i).Age+")"+" - "+familyList.get(i).HouseNoEn);
                }
                Log.d("TAG", "run: "+familyList.size());
                Intent intent = new Intent(SearchByNameList.this,VoterDetails.class);
                intent.putParcelableArrayListExtra("voterdata", (ArrayList<? extends Parcelable>) familyList);
                intent.putStringArrayListExtra("voterlist", name);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(SearchByNameList.this);
                ArrayList<VoterListData > voterData = getIntent().getParcelableArrayListExtra("voterdata");
                for (int i=0; i< voterData.size();i++) {
                    List<String> educstatus = pollFirstDataBase.pollFirstDao().checkVoterOCCUStatus(voterData.get(i).Voter_ID);
                    Log.d("TAG", "run: "+educstatus.get(0));
                    int finalI = i;
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (!educstatus.get(0).equals("null")) {
                                listView.setItemChecked(finalI,true);
                            }
                            else {
                                listView.setItemChecked(finalI,false);
//                                updateButton.setEnabled(false);
                                status = "pending";
                            }

                        }
                    });


                }

            }
        });
    }
}