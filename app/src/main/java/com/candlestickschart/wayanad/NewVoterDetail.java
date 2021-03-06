package com.candlestickschart.wayanad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    EditText formNumTxt;

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
        formNumTxt = findViewById(R.id.formNumber);
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
        if (getIntent().getStringExtra("type").equals("update")) {
            setData();
        }
    }

    public void setData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(NewVoterDetail.this);
                List<NewVoterData>newVoterData = pollFirstDataBase.pollFirstDao().getNewVoterById(getIntent().getIntExtra("id",0));
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(newVoterData.get(0).New_Name);
                        mobile.setText(newVoterData.get(0).New_Mobile);
                        dob.setText(newVoterData.get(0).New_DOB);
                        formNumTxt.setText(newVoterData.get(0).famgrp_id);
                    }
                });
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
            if (name.getText().toString().equals("")) {
                Toast.makeText(NewVoterDetail.this,"Enter Name",Toast.LENGTH_SHORT).show();
                return;
            }
            if (gender.getSelectedItem().toString().equals("Gender")) {
                Toast.makeText(NewVoterDetail.this,"Select Gender",Toast.LENGTH_SHORT).show();
                return;
            }

            if (dob.getText().toString().equals("")) {
                Toast.makeText(NewVoterDetail.this,"Enter DOB",Toast.LENGTH_SHORT).show();
                return;
            }
            if (formNumTxt.getText().toString().equals("")) {
                Toast.makeText(NewVoterDetail.this,"Enter Form Number",Toast.LENGTH_SHORT).show();
                return;
            }
            if (mobile.getText().toString().equals("")) {
                Toast.makeText(NewVoterDetail.this,"Enter Mobile",Toast.LENGTH_SHORT).show();
                return;
            }
            if (mobile.getText().toString().length()<10) {
                Toast.makeText(NewVoterDetail.this,"Invalid Mobile",Toast.LENGTH_SHORT).show();
                return;
            }
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {

                            PollFirstDataBase pollFirstDataBase = PollFirstDataBase.getInstance(NewVoterDetail.this);
                            if (getIntent().getStringExtra("type").equals("update")) {
                                pollFirstDataBase.pollFirstDao().updateNewVoterById(name.getText().toString(),gender.getSelectedItem().toString(),dob.getText().toString(),mobile.getText().toString(),sharedPreferences.getString("acno","")+"_"+sharedPreferences.getString("booth_no","")+"_"+formNumTxt.getText().toString(),getIntent().getIntExtra("id",0));
                                finish();
                            }
                            else {
                                NewVoterData pollFirstData = new NewVoterData(getIntent().getStringExtra("C_HOUSE_NO"),name.getText().toString(),gender.getSelectedItem().toString(),dob.getText().toString(),mobile.getText().toString(),sharedPreferences.getString("booth_no",""),sharedPreferences.getString("acno","")+"_"+sharedPreferences.getString("booth_no","")+"_"+formNumTxt.getText().toString());
                                pollFirstDataBase.pollFirstDao().insertNewVoter(pollFirstData);
                                finish();
                            }



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