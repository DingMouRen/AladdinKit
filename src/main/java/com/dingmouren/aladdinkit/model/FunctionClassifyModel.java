package com.dingmouren.aladdinkit.model;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public class FunctionClassifyModel implements Serializable {
    public String menuClassify;
    public List<FunctionModel> functionModelList;

    public FunctionClassifyModel(String menuClassify, List<FunctionModel> functionModelList) {
        this.menuClassify = menuClassify;
        this.functionModelList = functionModelList;
    }
}
