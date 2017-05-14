package com.orishkevich.accelerometerapp.model;

import java.util.ArrayList;


public class DaysModel {

    private ArrayList<Session> sessions;
    private String dateName;

    public DaysModel(ArrayList<Session> sessions, String dateName) {
        this.sessions = sessions;
        this.dateName = dateName;
    }

    public DaysModel() {

    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }


}
