package com.dingmouren.aladdinkit.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class FunctionModel implements Serializable {
    public Drawable img;
    public String desc;

    public FunctionModel(String desc, Drawable img ) {
        this.img = img;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "FunctionModel{" +
                "img=" + img +
                ", desc='" + desc + '\'' +
                '}';
    }
}
