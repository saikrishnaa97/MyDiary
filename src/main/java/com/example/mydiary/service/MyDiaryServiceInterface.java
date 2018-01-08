package com.example.mydiary.service;

import com.example.mydiary.dto.*;

public interface MyDiaryServiceInterface {
    GeneralResponse registerUser(NewUserRequest newUserRequest);
    GeneralResponse checkLogin(CheckLoginRequest checkLoginRequest);
    HomeResponse getHome(String emailId);
    AllEntriesResponse getAllEntries(String emailId);
    GeneralResponse addNewEntry(NewEntryRequest newEntryRequest);
    GeneralResponse deleteEntry(String entryId);
    GeneralResponse changeNotifyTime(ChangeTimeRequest changeTimeRequest);
    AllEntriesResponse getEntriesByDate(SearchRequest searchRequest);
}
