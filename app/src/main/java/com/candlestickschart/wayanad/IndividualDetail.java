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
import android.widget.RadioButton;

import com.candlestickschart.wayanad.databinding.ActivityCommunityBinding;
import com.candlestickschart.wayanad.databinding.ActivityIndividualDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IndividualDetail extends AppCompatActivity {

    JSONObject jsonObject;
    RadioButton radioButton1;
    RadioButton radioButton2;
    EditText residence;
    EditText dob;
    EditText anniversary;
    EditText mobile;
    EditText whatsapp;
    SharedPreferences sharedPreferences;
    Calendar myCalendar;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_detail);

        ActivityIndividualDetailBinding searchVoterBinding = DataBindingUtil.setContentView(this,R.layout.activity_individual_detail);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        User user = new User(sharedPreferences.getString("name",""),sharedPreferences.getString("vs_no","")+"-"+sharedPreferences.getString("vs_name",""),sharedPreferences.getString("booth_no","")+"-"+sharedPreferences.getString("booth_name",""),sharedPreferences.getString("search",""));
        searchVoterBinding.setMainUser(user);

        jsonObject = new JSONObject();

        radioButton1 = findViewById(R.id.yes);
        radioButton2 = findViewById(R.id.no);
        residence = findViewById(R.id.currentResidence);
        dob = findViewById(R.id.dob);
        anniversary = findViewById(R.id.anniversary);
        mobile = findViewById(R.id.mobile);
        whatsapp = findViewById(R.id.whastapp);
        myCalendar = Calendar.getInstance();
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("Voter_Place","YES");
                    residence.setEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("Voter_Place","NO");
                    residence.setEnabled(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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

        anniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "anniversary";
                new DatePickerDialog(IndividualDetail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "dob";
                new DatePickerDialog(IndividualDetail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (type.equals("dob")) {
            dob.setText(sdf.format(myCalendar.getTime()));
        }
        else {
            anniversary.setText(sdf.format(myCalendar.getTime()));
        }

    }


    public void backPressed(View view) {
        finish();
    }
    public void forwardPressed (View view){
        try {
            jsonObject.put("Current_Residence",residence.getText().toString());
            jsonObject.put("EPIC_NO",getIntent().getStringExtra("EPIC_NO"));
            jsonObject.put("Voter_DOB",dob.getText().toString());
            jsonObject.put("Voter_Anniversary",anniversary.getText().toString());
            jsonObject.put("Voter_Mobile",mobile.getText().toString());
            jsonObject.put("Voter_Whatsapp",whatsapp.getText().toString());
            Intent intent = new Intent(this,Education.class);
            intent.putExtra("json",jsonObject.toString());
            intent.putExtra("voterlist",getIntent().getStringArrayListExtra("voterlist"));
            intent.putExtra("voterdata",getIntent().getParcelableArrayListExtra("voterdata"));
            startActivity(intent);
//            if (!residence.getText().toString().equals("")) {
//
//
//                if (!dob.getText().toString().equals("")) {
//
//                    if (!anniversary.getText().toString().equals("")) {
//
//                        if (!mobile.getText().toString().equals("")) {
//
//                            if (!whatsapp.getText().toString().equals("")) {
//
//                            }
//                        }
//                    }
//
//                }
//            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}