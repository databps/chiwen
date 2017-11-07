package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.HdpAppUser;

import java.util.List;

import com.databps.bigdaf.admin.domain.HdpAppUser;
import java.util.List;

/**
 * HdpAppUserVo
 *
 * @author lgc
 * @create 2017-08-10 下午2:23
 */
public class HdpAppUserVo {
    List<HdpAppUser> hdpAppUserList;

    public List<HdpAppUser> getHdpAppUserList() {
        return hdpAppUserList;
    }

    public void setHdpAppUserList(List<HdpAppUser> hdpAppUserList) {
        this.hdpAppUserList = hdpAppUserList;
    }
}