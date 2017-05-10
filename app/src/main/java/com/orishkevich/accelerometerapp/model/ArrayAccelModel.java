package com.orishkevich.accelerometerapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Инженер-конструктор on 10.05.2017.
 */

public class ArrayAccelModel {

    private ArrayList<AccelModel> acMod;

    public ArrayList<AccelModel> getAccelModel() {
        return acMod;
    }

    public void setWords(ArrayList<AccelModel> acMod) {
        this.acMod = acMod;
    }

    public ArrayAccelModel(ArrayList<AccelModel> acMod) {
        this.acMod = acMod;
    }



}
