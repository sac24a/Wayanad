package com.candlestickschart.wayanad;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "voterlist")
public class VoterListData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "State")
    public String State;
    @ColumnInfo(name = "LS_No")
    public String LS_No;
    @ColumnInfo(name = "ACNo")
    public String ACNo;
    @ColumnInfo(name = "ACNameEn")
    public String ACNameEn;
    @ColumnInfo(name = "ACName")
    public String ACName;
    @ColumnInfo(name = "Booth_ID")
    public String Booth_ID;
    @ColumnInfo(name = "PartNo")
    public String PartNo;
    @ColumnInfo(name = "SectionNo")
    public String SectionNo;
    @ColumnInfo(name = "SectionNameEn")
    public String SectionNameEn;
    @ColumnInfo(name = "Fam_ID")
    public String Fam_ID;
    @ColumnInfo(name = "Voter_ID")
    public String Voter_ID;
    @ColumnInfo(name = "SNo")
    public String SNo;
    @ColumnInfo(name = "HouseNoEn")
    public String HouseNoEn;
    @ColumnInfo(name = "HouseNo")
    public String HouseNo;
    @ColumnInfo(name = "Voter_name")
    public String Voter_name;
    @ColumnInfo(name = "Voter_name_MAL")
    public String Voter_name_MAL;
    @ColumnInfo(name = "RelationType")
    public String RelationType;
    @ColumnInfo(name = "Rel_name")
    public String Rel_name;
    @ColumnInfo(name = "Rel_name_MAL")
    public String Rel_name_MAL;
    @ColumnInfo(name = "VoterID")
    public String VoterID;
    @ColumnInfo(name = "Sex")
    public String Sex;
    @ColumnInfo(name = "Age")
    public String Age;
    @ColumnInfo(name = "ContactNo")
    public String ContactNo;
    @ColumnInfo(name = "HOF")
    public String HOF;
    @ColumnInfo(name = "Segment")
    public String Segment;
    @ColumnInfo(name = "Community")
    public String Community;
    @ColumnInfo(name = "Ration_card")
    public String Ration_card;
    @ColumnInfo(name = "Land_holding")
    public String Land_holding;
    @ColumnInfo(name = "Pol_affinity")
    public String Pol_affinity;
    @ColumnInfo(name = "Livelihood")
    public String Livelihood;
    @ColumnInfo(name = "Live_Here")
    public String Live_Here;
    @ColumnInfo(name = "Alt_address")
    public String Alt_address;
    @ColumnInfo(name = "DOB")
    public String DOB;
    @ColumnInfo(name = "Anniversary")
    public String Anniversary;
    @ColumnInfo(name = "Whatsapp_no")
    public String Whatsapp_no;
    @ColumnInfo(name = "Education")
    public String Education;
    @ColumnInfo(name = "Occupation")
    public String Occupation;
    @ColumnInfo(name = "Status")
    public String Status;
    @ColumnInfo(name = "Vehicle")
    public String Vehicle;

    public VoterListData(int id,
     String State,
     String LS_No,
     String ACNo,
     String ACNameEn,
     String ACName,
     String Booth_ID,
     String PartNo,
     String SectionNo,
     String SectionNameEn,
     String Fam_ID,
     String Voter_ID,
     String SNo,
     String HouseNoEn,
     String HouseNo,
     String Voter_name,
     String Voter_name_MAL,
     String RelationType,
     String Rel_name,
     String Rel_name_MAL,
     String VoterID,
     String Sex,
     String Age,
     String ContactNo,
     String HOF,
     String Segment,
     String Community,
     String Ration_card,
     String Land_holding,
     String Pol_affinity,
     String Livelihood,
    String Live_Here,
    String Alt_address,
    String DOB,
    String Anniversary,
    String Whatsapp_no,
    String Education,
    String Occupation,String Status,String Vehicle){
        this.id=id;
        this.State=State;
        this.LS_No=LS_No;
        this.ACNo=ACNo;
        this.ACNameEn=ACNameEn;
        this.ACName=ACName;
        this.Booth_ID=Booth_ID;
        this.PartNo=PartNo;
        this.SectionNo=SectionNo;
        this.SectionNameEn=SectionNameEn;
        this.Fam_ID=Fam_ID;
        this.Voter_ID=Voter_ID;
        this.SNo=SNo;
        this.HouseNoEn=HouseNoEn;
        this.HouseNo=HouseNo;
        this.Voter_name=Voter_name;
        this.Voter_name_MAL=Voter_name_MAL;
        this.RelationType=RelationType;
        this.Rel_name=Rel_name;
        this.Rel_name_MAL=Rel_name_MAL;
        this.VoterID=VoterID;
        this.Sex=Sex;
        this.Age=Age;
        this.ContactNo=ContactNo;
        this.HOF=HOF;
        this.Segment=Segment;
        this.Community=Community;
        this.Ration_card=Ration_card;
        this.Land_holding=Land_holding;
        this.Pol_affinity=Pol_affinity;
        this.Livelihood=Livelihood;
        this.Live_Here=Live_Here;
        this.Alt_address=Alt_address;
        this.DOB=DOB;
        this.Anniversary=Anniversary;
        this.Whatsapp_no=Whatsapp_no;
        this.Education=Education;
        this.Occupation=Occupation;
        this.Status=Status;
        this.Vehicle=Vehicle;


    }

    @Ignore
    public VoterListData(String State,
                         String LS_No,
                         String ACNo,
                         String ACNameEn,
                         String ACName,
                         String Booth_ID,
                         String PartNo,
                         String SectionNo,
                         String SectionNameEn,
                         String Fam_ID,
                         String Voter_ID,
                         String SNo,
                         String HouseNoEn,
                         String HouseNo,
                         String Voter_name,
                         String Voter_name_MAL,
                         String RelationType,
                         String Rel_name,
                         String Rel_name_MAL,
                         String VoterID,
                         String Sex,
                         String Age,
                         String ContactNo,
                         String HOF,
                         String Segment,
                         String Community,
                         String Ration_card,
                         String Land_holding,
                         String Pol_affinity,
                         String Livelihood,
                         String Live_Here,
                         String Alt_address,
                         String DOB,
                         String Anniversary,
                         String Whatsapp_no,
                         String Education,
                         String Occupation,String Status,String Vehicle){

        this.id=id;
        this.State=State;
        this.LS_No=LS_No;
        this.ACNo=ACNo;
        this.ACNameEn=ACNameEn;
        this.ACName=ACName;
        this.Booth_ID=Booth_ID;
        this.PartNo=PartNo;
        this.SectionNo=SectionNo;
        this.SectionNameEn=SectionNameEn;
        this.Fam_ID=Fam_ID;
        this.Voter_ID=Voter_ID;
        this.SNo=SNo;
        this.HouseNoEn=HouseNoEn;
        this.HouseNo=HouseNo;
        this.Voter_name=Voter_name;
        this.Voter_name_MAL=Voter_name_MAL;
        this.RelationType=RelationType;
        this.Rel_name=Rel_name;
        this.Rel_name_MAL=Rel_name_MAL;
        this.VoterID=VoterID;
        this.Sex=Sex;
        this.Age=Age;
        this.ContactNo=ContactNo;
        this.HOF=HOF;
        this.Segment=Segment;
        this.Community=Community;
        this.Ration_card=Ration_card;
        this.Land_holding=Land_holding;
        this.Pol_affinity=Pol_affinity;
        this.Livelihood=Livelihood;
        this.Live_Here=Live_Here;
        this.Alt_address=Alt_address;
        this.DOB=DOB;
        this.Anniversary=Anniversary;
        this.Whatsapp_no=Whatsapp_no;
        this.Education=Education;
        this.Occupation=Occupation;
        this.Status=Status;
        this.Vehicle=Vehicle;

    }

    protected VoterListData(Parcel in) {
        id = in.readInt();
        State = in.readString();
        LS_No = in.readString();
        ACNo = in.readString();
        ACNameEn = in.readString();
        ACName = in.readString();
        Booth_ID = in.readString();
        PartNo = in.readString();
        SectionNo = in.readString();
        SectionNameEn = in.readString();
        Fam_ID = in.readString();
        Voter_ID = in.readString();
        SNo = in.readString();
        HouseNoEn = in.readString();
        HouseNo = in.readString();
        Voter_name = in.readString();
        Voter_name_MAL = in.readString();
        RelationType = in.readString();
        Rel_name = in.readString();
        Rel_name_MAL = in.readString();
        VoterID = in.readString();
        Sex = in.readString();
        Age = in.readString();
        ContactNo = in.readString();
        HOF = in.readString();
        Segment = in.readString();
        Community = in.readString();
        Ration_card = in.readString();
        Land_holding = in.readString();
        Pol_affinity = in.readString();
        Livelihood = in.readString();
        Live_Here = in.readString();
        Alt_address = in.readString();
        DOB = in.readString();
        Anniversary = in.readString();
        Whatsapp_no = in.readString();
        Education = in.readString();
        Occupation = in.readString();
        Status = in.readString();
        Vehicle = in.readString();

    }

    public static final Creator<VoterListData> CREATOR = new Creator<VoterListData>() {
        @Override
        public VoterListData createFromParcel(Parcel in) {
            return new VoterListData(in);
        }

        @Override
        public VoterListData[] newArray(int size) {
            return new VoterListData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(State);
        parcel.writeString(LS_No);
        parcel.writeString(ACNo);
        parcel.writeString(ACNameEn);
        parcel.writeString(ACName);
        parcel.writeString(Booth_ID);
        parcel.writeString(PartNo);
        parcel.writeString(SectionNo);
        parcel.writeString(SectionNameEn);
        parcel.writeString(Fam_ID);
        parcel.writeString(Voter_ID);
        parcel.writeString(SNo);
        parcel.writeString(HouseNoEn);
        parcel.writeString(HouseNo);
        parcel.writeString(Voter_name);
        parcel.writeString(Voter_name_MAL);
        parcel.writeString(RelationType);
        parcel.writeString(Rel_name);
        parcel.writeString(Rel_name_MAL);
        parcel.writeString(VoterID);
        parcel.writeString(Sex);
        parcel.writeString(Age);
        parcel.writeString(ContactNo);
        parcel.writeString(HOF);
        parcel.writeString(Segment);
        parcel.writeString(Community);
        parcel.writeString(Ration_card);
        parcel.writeString(Land_holding);
        parcel.writeString(Pol_affinity);
        parcel.writeString(Livelihood);
        parcel.writeString(Live_Here);
        parcel.writeString(Alt_address);
        parcel.writeString(DOB);
        parcel.writeString(Anniversary);
        parcel.writeString(Whatsapp_no);
        parcel.writeString(Education);
        parcel.writeString(Occupation);
        parcel.writeString(Status);
        parcel.writeString(Vehicle);
    }
}
