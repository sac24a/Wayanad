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
import android.widget.TextView;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityNewVoterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewVoter extends AppCompatActivity {

    String community;
    JSONObject jsonObject;
    List<String> religionName = new ArrayList<>();
    ListView listView;
    SharedPreferences sharedPreferences;
    TextView textView;
    List<NewVoterData> newVoterData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_voter);

        ActivityNewVoterBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_voter);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.clickNext);
        textView.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewVoter.this,NewVoterDetail.class);
                intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
                intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
                intent.putExtra("C_HOUSE_NO",getIntent().getStringExtra("C_HOUSE_NO"));
                intent.putExtra("type","update");
                intent.putExtra("id",newVoterData.get(i).id);
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
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(NewVoter.this);
                religionName = pollFirstDataBase.pollFirstDao().getNewVoterName(getIntent().getStringExtra("C_HOUSE_NO"));
                newVoterData = pollFirstDataBase.pollFirstDao().getNewVoter(getIntent().getStringExtra("C_HOUSE_NO"));
                if (religionName.size()!=0) {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.GONE);
                            ArrayAdapter<String> itemsAdapter =
                                    new ArrayAdapter<String>(NewVoter.this, android.R.layout.simple_list_item_1, religionName);
                            listView.setAdapter(itemsAdapter);
                        }
                    });
                }
                else {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(this,NewVoterDetail.class);
        intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
        intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
        intent.putExtra("C_HOUSE_NO",getIntent().getStringExtra("C_HOUSE_NO"));
        intent.putExtra("type","add");
        startActivity(intent);
    }
}