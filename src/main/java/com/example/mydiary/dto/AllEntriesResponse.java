package com.example.mydiary.dto;

import java.util.List;

public class AllEntriesResponse {
    private GeneralResponse general;
    private List<Entry> entryList;
    private String emailId;
    private String name;

    public GeneralResponse getGeneral() {
        return general;
    }

    public void setGeneral(GeneralResponse general) {
        this.general = general;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
