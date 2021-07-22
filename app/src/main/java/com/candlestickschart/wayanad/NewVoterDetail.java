package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.Toast;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityNewVoterDetailBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewVoterDetail extends AppCompatActivity {

    EditText name;
    Spinner gender;
    EditText dob;
    EditText mobile;
    SharedPreferences sharedPreferences;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_voter_detail);

        ActivityNewVoterDetailBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_voter_detail);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        mobile = findViewById(R.id.mobile);
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewVoterDetail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));

    }

    public void backPressed(View view) {
        finish();
    }
    public void forwardPressed (View view){
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(NewVoterDetail.this);
                        NewVoterData pollFirstData = new NewVoterData(getIntent().getStringExtra("C_HOUSE_NO"),name.getText().toString(),gender.getSelectedItem().toString(),dob.getText().toString(),mobile.getText().toString());
                        pollFirstDataBase.pollFirstDao().insertNewVoter(pollFirstData);
                        finish();
                    }
                    catch (Exception e ) {

                    }
                }
            });
        }
        catch (NullPointerException e) {

        }
    }
}