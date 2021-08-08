package com.candlestickschart.wayanad;

import android.os.Parcel;
import android.os.Parcelable;

public class VoterData implements Parcelable {
    String Sno;
    String Name;
    String Gender;
    String Age;


    public VoterData(String Sno,
                     String Name,
                             String Gender,
                             String Age){
        this.Sno = Sno;
        this.Name = Name;
        this.Gender = Gender;
        this.Age = Age;

    }

    protected VoterData(Parcel in) {
        Sno = in.readString();
        Name = in.readString();
        Gender = in.readString();
        Age = in.readString();
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
        parcel.writeString(Sno);
        parcel.writeString(Name);
        parcel.writeString(Gender);
        parcel.writeString(Age);
    }
}
