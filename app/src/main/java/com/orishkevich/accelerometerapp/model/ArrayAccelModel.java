package com.orishkevich.accelerometerapp.model;

import java.util.ArrayList;

/**
 * Created by Инженер-конструктор on 10.05.2017.
 */

public class ArrayAccelModel {

    private String user;
    private String session;
    private ArrayList<Session> userSession;

    public ArrayAccelModel(String user, String session, ArrayList<Session> userSession) {
        this.user = user;
        this.session = session;
        this.userSession = userSession;
    }



    public ArrayAccelModel() {

    }



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }



    public ArrayList<Session> getUserSession() {
        return userSession;
    }

    public void setArray(ArrayList<Session> userSession) {
        this.userSession = userSession;
    }





}
