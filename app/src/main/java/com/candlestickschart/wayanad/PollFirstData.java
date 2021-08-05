package com.candlestickschart.wayanad;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "pollfirst")
public class PollFirstData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "user_id")
    public String user_id;
    @ColumnInfo(name = "user_mobile_no")
    public String user_mobile_no;
    @ColumnInfo(name = "LOC_ID")
    public String LOC_ID;
    @ColumnInfo(name = "VS_No")
    public String VS_No;
    @ColumnInfo(name = "LS_No")
    public String LS_No;
    @ColumnInfo(name = "AC_NO")
    public String AC_NO;
    @ColumnInfo(name = "PART_NO")
    public String PART_NO;
    @ColumnInfo(name = "SECTION_NO")
    public String SECTION_NO;
    @ColumnInfo(name = "SLNOINPART")
    public String SLNOINPART;
    @ColumnInfo(name = "C_HOUSE_NO")
    public String C_HOUSE_NO;
    @ColumnInfo(name = "C_HOUSE_NO_V1")
    public String C_HOUSE_NO_V1;
    @ColumnInfo(name = "FM_NAME_EN")
    public String FM_NAME_EN;
    @ColumnInfo(name = "LASTNAME_EN")
    public String LASTNAME_EN;
    @ColumnInfo(name = "FM_NAME_V1")
    public String FM_NAME_V1;
    @ColumnInfo(name = "LASTNAME_V1")
    public String LASTNAME_V1;
    @ColumnInfo(name = "RLN_TYPE")
    public String RLN_TYPE;
    @ColumnInfo(name = "RLN_FM_NM_EN")
    public String RLN_FM_NM_EN;
    @ColumnInfo(name = "RLN_L_NM_EN")
    public String RLN_L_NM_EN;
    @ColumnInfo(name = "RLN_FM_NM_V1")
    public String RLN_FM_NM_V1;
    @ColumnInfo(name = "RLN_L_NM_V1")
    public String RLN_L_NM_V1;
    @ColumnInfo(name = "EPIC_NO")
    public String EPIC_NO;
    @ColumnInfo(name = "GENDER")
    public String GENDER;
    @ColumnInfo(name = "AGE")
    public String AGE;
    @ColumnInfo(name = "DOB")
    public String DOB;
    @ColumnInfo(name = "MOBILE_NO")
    public String MOBILE_NO;
    @ColumnInfo(name = "Family_Head")
    public String Family_Head;
    @ColumnInfo(name = "Social_Group")
    public String Social_Group;
    @ColumnInfo(name = "Caste")
    public String Caste;
    @ColumnInfo(name = "Ration_Card")
    public String Ration_Card;
    @ColumnInfo(name = "Land_Holding")
    public String Land_Holding;
    @ColumnInfo(name = "Political_Affinity")
    public String Political_Affinity;
    @ColumnInfo(name = "Source_Livelihood")
    public String Source_Livelihood;
    @ColumnInfo(name = "Voter_Place")
    public String Voter_Place;
    @ColumnInfo(name = "Current_Residence")
    public String Current_Residence;
    @ColumnInfo(name = "Voter_DOB")
    public String Voter_DOB;
    @ColumnInfo(name = "Voter_Anniversary")
    public String Voter_Anniversary;
    @ColumnInfo(name = "Voter_Mobile")
    public String Voter_Mobile;
    @ColumnInfo(name = "Voter_Whatsapp")
    public String Voter_Whatsapp;
    @ColumnInfo(name = "Voter_EDU")
    public String Voter_EDU;
    @ColumnInfo(name = "Voter_OCCU")
    public String Voter_OCCU;
    @ColumnInfo(name = "New_Name")
    public String New_Name;
    @ColumnInfo(name = "New_Gender")
    public String New_Gender;
    @ColumnInfo(name = "New_DOB")
    public String New_DOB;
    @ColumnInfo(name = "New_Mobile")
    public String New_Mobile;

    public PollFirstData(int id,
                         String AC_NO,
                         String PART_NO,
                         String SECTION_NO,
                         String SLNOINPART,
                         String C_HOUSE_NO,
                         String C_HOUSE_NO_V1,
                         String FM_NAME_EN,
                         String LASTNAME_EN,
                         String FM_NAME_V1,
                         String LASTNAME_V1,
                         String RLN_TYPE,
                         String RLN_FM_NM_EN,
                         String RLN_L_NM_EN,
                         String RLN_FM_NM_V1,
                         String RLN_L_NM_V1,
                         String EPIC_NO,
                         String GENDER,
                         String AGE,
                         String DOB,
                         String MOBILE_NO,
                         String Family_Head,
                         String Social_Group,
                         String Caste,
                         String Ration_Card,
                         String Land_Holding,
                         String Political_Affinity,
                         String Source_Livelihood,
                         String Voter_Place,
                         String Current_Residence,
                         String Voter_DOB,
                         String Voter_Anniversary,
                         String Voter_Mobile,
                         String Voter_Whatsapp,
                         String Voter_EDU,
                         String Voter_OCCU,
                         String New_Name,
                         String New_Gender,
                         String New_DOB,
                         String New_Mobile){
        this.id = id;
        this.AC_NO = AC_NO;
        this.PART_NO = PART_NO;
        this.SECTION_NO = SECTION_NO;
        this.SLNOINPART = SLNOINPART;
        this.C_HOUSE_NO = C_HOUSE_NO;
        this.C_HOUSE_NO_V1 = C_HOUSE_NO_V1;
        this.FM_NAME_EN = FM_NAME_EN;
        this.LASTNAME_EN = LASTNAME_EN;
        this.FM_NAME_V1 = FM_NAME_V1;
        this.LASTNAME_V1 = LASTNAME_V1;
        this.RLN_TYPE = RLN_TYPE;
        this.RLN_FM_NM_EN = RLN_FM_NM_EN;
        this.RLN_L_NM_EN = RLN_L_NM_EN;
        this.RLN_L_NM_V1 = RLN_L_NM_V1;
        this.RLN_FM_NM_V1 = RLN_FM_NM_V1;
        this.EPIC_NO = EPIC_NO;
        this.GENDER = GENDER;
        this.AGE = AGE;
        this.DOB = DOB;
        this.MOBILE_NO = MOBILE_NO;
        this.Social_Group = Social_Group;
        this.Caste = Caste;
        this.Ration_Card = Ration_Card;
        this.Land_Holding = Land_Holding;
        this.Political_Affinity = Political_Affinity;
        this.Source_Livelihood = Source_Livelihood;
        this.Voter_Place = Voter_Place;
        this.Current_Residence = Current_Residence;
        this.Voter_DOB = Voter_DOB;
        this.Voter_Anniversary = Voter_Anniversary;
        this.Voter_Mobile = Voter_Mobile;
        this.Voter_Whatsapp = Voter_Whatsapp;
        this.Voter_EDU = Voter_EDU;
        this.Voter_OCCU = Voter_OCCU;
        this.Family_Head = Family_Head;

        this.New_DOB = New_DOB;
        this.New_Gender = New_Gender;
        this.New_Name = New_Name;
        this.New_Mobile = New_Mobile;


    }

    @Ignore
    public PollFirstData(String AC_NO,
                         String PART_NO,
                         String SECTION_NO,
                         String SLNOINPART,
                         String C_HOUSE_NO,
                         String C_HOUSE_NO_V1,
                         String FM_NAME_EN,
                         String LASTNAME_EN,
                         String FM_NAME_V1,
                         String LASTNAME_V1,
                         String RLN_TYPE,
                         String RLN_FM_NM_EN,
                         String RLN_L_NM_EN,
                         String RLN_FM_NM_V1,
                         String RLN_L_NM_V1,
                         String EPIC_NO,
                         String GENDER,
                         String AGE,
                         String DOB,
                         String MOBILE_NO,
                         String Family_Head,
                         String Social_Group,
                         String Caste,
                         String Ration_Card,
                         String Land_Holding,
                         String Political_Affinity,
                         String Source_Livelihood,
                         String Voter_Place,
                         String Current_Residence,
                         String Voter_DOB,
                         String Voter_Anniversary,
                         String Voter_Mobile,
                         String Voter_Whatsapp,
                         String Voter_EDU,
                         String Voter_OCCU,
                         String New_Name,
                         String New_Gender,
                         String New_DOB,
                         String New_Mobile){

        this.AC_NO = AC_NO;
        this.PART_NO = PART_NO;
        this.SECTION_NO = SECTION_NO;
        this.SLNOINPART = SLNOINPART;
        this.C_HOUSE_NO = C_HOUSE_NO;
        this.C_HOUSE_NO_V1 = C_HOUSE_NO_V1;
        this.FM_NAME_EN = FM_NAME_EN;
        this.LASTNAME_EN = LASTNAME_EN;
        this.FM_NAME_V1 = FM_NAME_V1;
        this.LASTNAME_V1 = LASTNAME_V1;
        this.RLN_TYPE = RLN_TYPE;
        this.RLN_FM_NM_EN = RLN_FM_NM_EN;
        this.RLN_L_NM_EN = RLN_L_NM_EN;
        this.RLN_L_NM_V1 = RLN_L_NM_V1;
        this.RLN_FM_NM_V1 = RLN_FM_NM_V1;
        this.EPIC_NO = EPIC_NO;
        this.GENDER = GENDER;
        this.AGE = AGE;
        this.DOB = DOB;
        this.MOBILE_NO = MOBILE_NO;
        this.Social_Group = Social_Group;
        this.Caste = Caste;
        this.Ration_Card = Ration_Card;
        this.Land_Holding = Land_Holding;
        this.Political_Affinity = Political_Affinity;
        this.Source_Livelihood = Source_Livelihood;
        this.Voter_Place = Voter_Place;
        this.Current_Residence = Current_Residence;
        this.Voter_DOB = Voter_DOB;
        this.Voter_Anniversary = Voter_Anniversary;
        this.Voter_Mobile = Voter_Mobile;
        this.Voter_Whatsapp = Voter_Whatsapp;
        this.Voter_EDU = Voter_EDU;
        this.Voter_OCCU = Voter_OCCU;
        this.Family_Head = Family_Head;
        this.New_DOB = New_DOB;
        this.New_Gender = New_Gender;
        this.New_Name = New_Name;
        this.New_Mobile = New_Mobile;

    }
}
