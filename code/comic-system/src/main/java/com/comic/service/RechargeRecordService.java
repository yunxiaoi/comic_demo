// RechargeRecordService.java
package com.comic.service;

import com.comic.entity.RechargeRecord;
import java.util.List;
import java.util.Map;

public interface RechargeRecordService {
    List<RechargeRecord> getByUserId(Integer userId);
    void addRecord(Integer userId, Double amount);
    Map<String, Object> getPage(int page, int size);
    // 分页查询所有充值记录
    Map<String, Object> listAll(int page, int size);
}