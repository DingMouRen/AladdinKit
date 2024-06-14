package com.dingmouren.aladdinkit.model;

import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * 环境切换数据模式，默认选中第一个
 */
@Keep
public class HostAddressModel implements Serializable {
    public String hostAddress;
    public boolean selected;
}
