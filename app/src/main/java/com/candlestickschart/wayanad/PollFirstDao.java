package com.candlestickschart.wayanad;

import android.content.Intent;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PollFirstDao {
    @Query("Select * from pollfirst")
    List<PollFirstData> getPollfirstData();

    @Query("Select * from voterlist")
    List<VoterListData> getVoterList();

    @Query("Select Fam_ID from voterlist WHERE SNo = :value")
    String searchVoterList(String value);

    @Query("Select * from voterlist WHERE Fam_ID = :value")
    List<VoterListData> searchVoterFamilyList(String value);

    @Query("Select * from voterlist WHERE Voter_ID = :value")
    List<VoterListData> getVoterDetails(String value);

    @Query("Select * from voterlist WHERE Status = :value")
    List<VoterListData> getCompletedVoterDetails(String value);

    @Query("Select * from newvoter")
    List<NewVoterData> getNewVoterData();

    @Query("Select EPIC_NO from pollfirst WHERE EPIC_NO = :epicno")
    List<String> checkPollfirstData(String epicno);

    @Query("Select Occupation from voterlist WHERE Voter_ID = :epicno")
    List<String> checkVoterOCCUStatus(String epicno);

    @Query("Select New_Name from newvoter WHERE C_HOUSE_NO = :epicno")
    List<String> getNewVoterName(String epicno);

    @Query("Select * from newvoter WHERE C_HOUSE_NO = :epicno")
    List<NewVoterData> getNewVoter(String epicno);

    @Query("Select * from newvoter WHERE id = :epicno")
    List<NewVoterData> getNewVoterById(Integer epicno);

    @Query("UPDATE newvoter SET New_Name=:name, New_Gender=:gender, New_DOB=:dob, New_Mobile=:mobile, famgrp_id=:famgrp_id WHERE id = :epicno")
    void updateNewVoterById(String name, String gender, String dob, String mobile,String famgrp_id, Integer epicno);

    @Query("UPDATE voterlist SET Live_Here=:place, Alt_address=:residence, DOB=:dob, Anniversary=:anniversary, Whatsapp_no=:mobile, Education=:edu, Occupation=:occu, Segment=:community, Community=:caste, Ration_card=:ration, Land_holding=:land, Pol_affinity=:political, Livelihood=:livelihood, Status=:status, Vehicle=:vehicle, famgrp_id=:famgrp_id WHERE Voter_ID = :epicno")
    void updateVoterIndividual(String place,String residence,String dob,String anniversary,String mobile,String edu,String occu,String epicno,String community,String caste,String ration,String land,String political,String livelihood,String status,String vehicle,String famgrp_id);

    @Query("UPDATE voterlist SET HOF=:head, Segment=:community, Community=:caste, Ration_card=:ration, Land_holding=:land, Pol_affinity=:political, Livelihood=:livelihood, Status=:status, Vehicle=:vehicle WHERE Fam_ID = :epicno")
    void updateVoterDetail(String head,String community,String caste,String ration,String land,String political,String livelihood,String epicno,String status,String vehicle);

    @Query("DELETE FROM voterlist")
    void clearVoterTable();
    @Query("DELETE FROM newvoter")
    void clearNewVoterTable();

    @Insert
    void insertPollFirst(PollFirstData pollFirstData);

    @Insert
    void insertVoterList(VoterListData pollFirstData);

    @Insert
    void insertNewVoter(NewVoterData pollFirstData);


    @Delete
    void deletePollFirst(PollFirstData pollFirstData);
}
