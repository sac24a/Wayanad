package com.candlestickschart.wayanad;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PollFirstDao {
    @Query("Select * from pollfirst")
    List<PollFirstData> getPollfirstData();

    @Query("Select * from newvoter")
    List<NewVoterData> getNewVoterData();

    @Query("Select EPIC_NO from pollfirst WHERE EPIC_NO = :epicno")
    List<String> checkPollfirstData(String epicno);

    @Query("Select Voter_OCCU from pollfirst WHERE EPIC_NO = :epicno")
    List<String> checkVoterOCCUStatus(String epicno);

    @Query("Select New_Name from newvoter WHERE C_HOUSE_NO = :epicno")
    List<String> getNewVoterName(String epicno);

    @Query("UPDATE pollfirst SET Voter_Place=:place, Current_Residence=:residence, Voter_DOB=:dob, Voter_Anniversary=:anniversary, Voter_Mobile=:mobile, Voter_Whatsapp=:whatsapp, Voter_EDU=:edu, Voter_OCCU=:occu WHERE EPIC_NO = :epicno")
    void updateVoterIndividual(String place,String residence,String dob,String anniversary,String mobile,String whatsapp,String edu,String occu,String epicno);

    @Query("UPDATE pollfirst SET Family_Head=:head, Social_Group=:community, Caste=:caste, Ration_Card=:ration, Land_Holding=:land, Political_Affinity=:political, Source_Livelihood=:livelihood WHERE C_HOUSE_NO = :epicno")
    void updateVoterDetail(String head,String community,String caste,String ration,String land,String political,String livelihood,String epicno);

    @Query("DELETE FROM pollfirst")
    void clearPollfirstTable();

    @Query("DELETE FROM newvoter")
    void clearNewVoterTable();

    @Insert
    void insertPollFirst(PollFirstData pollFirstData);

    @Insert
    void insertNewVoter(NewVoterData pollFirstData);


    @Delete
    void deletePollFirst(PollFirstData pollFirstData);
}
