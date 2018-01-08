package com.example.mydiary.service.impl;

import com.example.mydiary.dao.EntryRepository;
import com.example.mydiary.dao.ProfileRepository;
import com.example.mydiary.dto.*;
import com.example.mydiary.entity.Entry;
import com.example.mydiary.entity.UserProfile;
import com.example.mydiary.service.MyDiaryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MyDiaryServiceImpl implements MyDiaryServiceInterface {

   @Autowired
    ProfileRepository profileRepository;
    @Autowired
    EntryRepository entryRepository;

    @Override
    public GeneralResponse registerUser(NewUserRequest newUserRequest) {
        UserProfile user = new UserProfile();
        GeneralResponse response = new GeneralResponse();
        user.setDateOfBirth(newUserRequest.getDob());
        user.setEmailId(newUserRequest.getEmailId());
        user.setMobileNumber(newUserRequest.getMobileNumber());
        user.setName(newUserRequest.getName());
        user.setNotifyHrs(newUserRequest.getNotifyHrs());
        user.setNotifyMins(newUserRequest.getNotifyMins());
        user.setPassword(newUserRequest.getPassword());
        user.setUserName(newUserRequest.getUserName());
        user.setConsec_days(0);
        if (profileRepository.save(user) != null) {
            response.setStatusCode(1);
            response.setErrorMessage("Registered Successfully");
        } else {
            response.setStatusCode(0);
            response.setErrorMessage("Could not register.");
        }
        return response;
    }

    @Override
    public GeneralResponse checkLogin(CheckLoginRequest checkLoginRequest) {
        UserProfile user = profileRepository.findByUserName(checkLoginRequest.getUserName());
        GeneralResponse response = new GeneralResponse();
        System.out.println(checkLoginRequest.getUserName());
        if (user != null) {
            if (user.getPassword().equals(checkLoginRequest.getPassword())) {
                response.setStatusCode(1);
                response.setErrorMessage("You have successfully logged in");
            } else {
                response.setStatusCode(0);
                response.setErrorMessage("Password wrong");
            }
        } else {

            response.setStatusCode(0);
            response.setErrorMessage("Username wrong");
        }
        return response;
    }

    @Override
    public HomeResponse getHome(String emailId) {
        HomeResponse response = new HomeResponse();
        UserProfile user = profileRepository.findByEmailId(emailId);
        GeneralResponse general = new GeneralResponse();
        AllEntriesResponse allEntriesResponse = new AllEntriesResponse();
        if (user != null) {
            List<Entry> entryList = entryRepository.findTop10ByMarkedForDeleteFalseAndIdOrderByDateOfEntryDesc(user.getId());
            if (entryList != null) {
                List<com.example.mydiary.dto.Entry> resultEntryList = new ArrayList<>();
                for (Entry entry : entryList) {
                    com.example.mydiary.dto.Entry resultEntry = new com.example.mydiary.dto.Entry();
                    resultEntry.setId(entry.getId());
                    resultEntry.setDoe(entry.getDateOfEntry());
                    resultEntry.setMessage(entry.getMessage());
                    resultEntry.setTitle(entry.getTitle());
                    resultEntryList.add(resultEntry);
                }
                allEntriesResponse.setEntryList(resultEntryList);
            }
            response.setNotifyHrs(user.getNotifyHrs());
            response.setNotifyMins(user.getNotifyMins());
            allEntriesResponse.setEmailId(emailId);
            allEntriesResponse.setName(user.getName());
            general.setStatusCode(1);
        } else {
            general.setStatusCode(0);
            general.setErrorMessage("User Not Found");
        }
        allEntriesResponse.setGeneral(general);
        response.setAllentries(allEntriesResponse);
        return response;
    }

    @Override
    public AllEntriesResponse getAllEntries(String emailId) {
        AllEntriesResponse allEntriesResponse = new AllEntriesResponse();
        UserProfile user = profileRepository.findByEmailId(emailId);
        GeneralResponse general = new GeneralResponse();
        if (user != null) {
            List<Entry> entryList = entryRepository.findByMarkedForDeleteFalseAndId(user.getId());
            if (entryList != null) {
                List<com.example.mydiary.dto.Entry> resultEntryList = new ArrayList<>();
                for (Entry entry : entryList) {
                    com.example.mydiary.dto.Entry resultEntry = new com.example.mydiary.dto.Entry();
                    resultEntry.setId(entry.getId());
                    resultEntry.setDoe(entry.getDateOfEntry());
                    resultEntry.setMessage(entry.getMessage());
                    resultEntry.setTitle(entry.getTitle());
                    resultEntryList.add(resultEntry);
                }
                allEntriesResponse.setEntryList(resultEntryList);
            }
            allEntriesResponse.setName(user.getName());
            allEntriesResponse.setEmailId(emailId);
            general.setStatusCode(1);
            allEntriesResponse.setGeneral(general);
        } else {
            general.setStatusCode(0);
            general.setErrorMessage("User not found");
        }
        return allEntriesResponse;
    }

    @Override
    public GeneralResponse addNewEntry(NewEntryRequest newEntryRequest) {
        GeneralResponse general = new GeneralResponse();
        UserProfile user = profileRepository.findByEmailId(newEntryRequest.getEmailId());
        if(user != null) {
            Entry newEntry = new Entry();
            newEntry.setDateOfEntry(new Date());
            newEntry.setMarkedForDelete(false);
            newEntry.setProfileId(user.getId());
            newEntry.setMessage(newEntryRequest.getMessage());
            newEntry.setTitle(newEntryRequest.getTitle());
            if(entryRepository.save(newEntry) != null) {
                general.setStatusCode(1);
            }
            else {
                general.setErrorMessage("Could not add the entry");
            }
        }
        return general;
    }

    @Override
    public GeneralResponse deleteEntry(String entryId) {
        GeneralResponse general = new GeneralResponse();
        Entry entry = entryRepository.findOne(entryId);
        entry.setMarkedForDelete(true);
        if(entryRepository.save(entry) != null){
            general.setStatusCode(1);
        }
        else {
            general.setErrorMessage("Could not delete the entry");
        }
        return general;
    }

    @Override
    public GeneralResponse changeNotifyTime(ChangeTimeRequest changeTimeRequest) {
        GeneralResponse response = new GeneralResponse();
        UserProfile user = profileRepository.findOne(changeTimeRequest.getProfileId());
        if (user != null) {
            user.setNotifyMins(changeTimeRequest.getNotifyMins());
            user.setNotifyHrs(changeTimeRequest.getNotifyHrs());
            if (profileRepository.save(user) != null) {
                response.setStatusCode(1);
            } else {
                response.setErrorMessage("Could not update Notify time");
            }
        }
        return response;
    }

    @Override
    public AllEntriesResponse getEntriesByDate(SearchRequest searchRequest) {
        AllEntriesResponse response = new AllEntriesResponse();
        UserProfile user = profileRepository.findOne(searchRequest.getProfileId());
        GeneralResponse general = new GeneralResponse();
        if (user != null) {
            List<Entry> entryList = entryRepository.findByMarkedForDeleteFalseAndProfileIdAndDateOfEntry(searchRequest.getProfileId(), searchRequest.getDoe());
            if (entryList != null) {
                List<com.example.mydiary.dto.Entry> resultEntryList = new ArrayList<>();
                for (Entry entry : entryList) {
                    com.example.mydiary.dto.Entry resultEntry = new com.example.mydiary.dto.Entry();
                    resultEntry.setId(entry.getId());
                    resultEntry.setDoe(entry.getDateOfEntry());
                    resultEntry.setMessage(entry.getMessage());
                    resultEntry.setTitle(entry.getTitle());
                    resultEntryList.add(resultEntry);
                }
                response.setEntryList(resultEntryList);
                response.setName(user.getName());
                response.setEmailId(user.getEmailId());
                general.setStatusCode(1);
                response.setGeneral(general);
            }
        }
        else {
            general.setErrorMessage("User not Found");
        }
        response.setGeneral(general);
        return response;
    }
}
