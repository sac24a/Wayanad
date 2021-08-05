package com.candlestickschart.wayanad;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "newvoter")
public class NewVoterData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "C_HOUSE_NO")
    public String C_HOUSE_NO;
    @ColumnInfo(name = "New_Name")
    public String New_Name;
    @ColumnInfo(name = "New_Gender")
    public String New_Gender;
    @ColumnInfo(name = "New_DOB")
    public String New_DOB;
    @ColumnInfo(name = "New_Mobile")
    public String New_Mobile;
    @ColumnInfo(name = "Booth_no")
    public String Booth_no;

    public NewVoterData(int id,
                         String C_HOUSE_NO,
                         String New_Name,
                         String New_Gender,
                         String New_DOB,
                         String New_Mobile,
                        String Booth_no){
        this.id = id;
        this.C_HOUSE_NO = C_HOUSE_NO;
        this.New_DOB = New_DOB;
        this.New_Gender = New_Gender;
        this.New_Name = New_Name;
        this.New_Mobile = New_Mobile;
        this.Booth_no = Booth_no;
    }
    @Ignore
    public NewVoterData(String C_HOUSE_NO,
                         String New_Name,
                         String New_Gender,
                         String New_DOB,
                         String New_Mobile,
                        String Booth_no){
        this.C_HOUSE_NO = C_HOUSE_NO;
        this.New_DOB = New_DOB;
        this.New_Gender = New_Gender;
        this.New_Name = New_Name;
        this.New_Mobile = New_Mobile;
        this.Booth_no = Booth_no;

    }
}
