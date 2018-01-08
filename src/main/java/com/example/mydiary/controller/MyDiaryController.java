package com.example.mydiary.controller;

import com.example.mydiary.dto.*;
import com.example.mydiary.service.MyDiaryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
public class MyDiaryController {

    @Autowired
    MyDiaryServiceInterface myDiaryServiceInterface;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "index.html";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/newuser")
    public GeneralResponse addNewUSer(@RequestBody NewUserRequest newUserRequest){
        return myDiaryServiceInterface.registerUser(newUserRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/checklogin")
    public GeneralResponse checkLogin(@RequestBody CheckLoginRequest checkLoginRequest){
        return myDiaryServiceInterface.checkLogin(checkLoginRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/home")
    public HomeResponse getHomeDetails(@RequestParam("emailId") String emailId){
        return myDiaryServiceInterface.getHome(emailId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/allentries")
    public AllEntriesResponse getAllEntries(@RequestParam("emailId") String emailId){
        return myDiaryServiceInterface.getAllEntries(emailId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addnew")
    public GeneralResponse addNewEntry(@RequestBody NewEntryRequest newEntryRequest){
        return myDiaryServiceInterface.addNewEntry(newEntryRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public GeneralResponse deleteEntry(@RequestParam String entryId){
        return myDiaryServiceInterface.deleteEntry(entryId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edittime")
    public GeneralResponse changeNotifyTime(@RequestBody ChangeTimeRequest changeTimeRequest){
        return myDiaryServiceInterface.changeNotifyTime(changeTimeRequest);
    }

    @RequestMapping(method=RequestMethod.POST , value="/search")
    public AllEntriesResponse getEntryByTime(@RequestBody SearchRequest searchRequest){
        return myDiaryServiceInterface.getEntriesByDate(searchRequest);
    }

}
