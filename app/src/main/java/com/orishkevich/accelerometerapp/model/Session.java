package com.orishkevich.accelerometerapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Инженер-конструктор on 10.05.2017.
 */
@IgnoreExtraProperties
public class Session {

    private String user;
    private String session;
    private ArrayList<AccelModel> userAccelModel;

    public Session(String user, String session, ArrayList<AccelModel> userAccelModel) {
        this.user = user;
        this.session = session;
        this.userAccelModel = userAccelModel;
    }



    public Session() {

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



    public ArrayList<AccelModel> getUserAccelModel() {
        return userAccelModel;
    }

    public void setArray(ArrayList<AccelModel> userAccelModel) {
        this.userAccelModel = userAccelModel;
    }





}
