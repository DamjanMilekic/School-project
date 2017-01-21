package com.example.laptop.icraudi;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class Model
{

    String name = null;

    public Model(String name) {
        super();

        this.name = name;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



}
