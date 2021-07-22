package com.candlestickschart.wayanad;

import android.os.Parcel;
import android.os.Parcelable;

public class VoterData implements Parcelable {
    String id;
    String AC_NO;
    String PART_NO;
    String SECTION_NO;
    String SLNOINPART;
    String C_HOUSE_NO;
    String C_HOUSE_NO_V1;
    String FM_NAME_EN;
    String LASTNAME_EN;
    String FM_NAME_V1;
    String LASTNAME_V1;
    String RLN_TYPE;
    String RLN_FM_NM_EN;
    String RLN_L_NM_EN;
    String RLN_FM_NM_V1;
    String RLN_L_NM_V1;
    String EPIC_NO;
    String GENDER;
    String AGE;
    String DOB;
    String MOBILE_NO;

    public VoterData(String id,
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
            String MOBILE_NO){
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

    }

    protected VoterData(Parcel in) {
        id = in.readString();
        AC_NO = in.readString();
        PART_NO = in.readString();
        SECTION_NO = in.readString();
        SLNOINPART = in.readString();
        C_HOUSE_NO = in.readString();
        C_HOUSE_NO_V1 = in.readString();
        FM_NAME_EN = in.readString();
        LASTNAME_EN = in.readString();
        FM_NAME_V1 = in.readString();
        LASTNAME_V1 = in.readString();
        RLN_TYPE = in.readString();
        RLN_FM_NM_EN = in.readString();
        RLN_L_NM_EN = in.readString();
        RLN_FM_NM_V1 = in.readString();
        RLN_L_NM_V1 = in.readString();
        EPIC_NO = in.readString();
        GENDER = in.readString();
        AGE = in.readString();
        DOB = in.readString();
        MOBILE_NO = in.readString();
    }

    public static final Creator<VoterData> CREATOR = new Creator<VoterData>() {
        @Override
        public VoterData createFromParcel(Parcel in) {
            return new VoterData(in);
        }

        @Override
        public VoterData[] newArray(int size) {
            return new VoterData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(AC_NO);
        parcel.writeString(PART_NO);
        parcel.writeString(SECTION_NO);
        parcel.writeString(SLNOINPART);
        parcel.writeString(C_HOUSE_NO);
        parcel.writeString(C_HOUSE_NO_V1);
        parcel.writeString(FM_NAME_EN);
        parcel.writeString(LASTNAME_EN);
        parcel.writeString(FM_NAME_V1);
        parcel.writeString(LASTNAME_V1);
        parcel.writeString(RLN_TYPE);
        parcel.writeString(RLN_FM_NM_EN);
        parcel.writeString(RLN_L_NM_EN);
        parcel.writeString(RLN_FM_NM_V1);
        parcel.writeString(RLN_L_NM_V1);
        parcel.writeString(EPIC_NO);
        parcel.writeString(GENDER);
        parcel.writeString(AGE);
        parcel.writeString(DOB);
        parcel.writeString(MOBILE_NO);
    }
}
