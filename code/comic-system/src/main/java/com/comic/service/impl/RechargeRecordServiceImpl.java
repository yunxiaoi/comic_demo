// RechargeRecordServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.RechargeRecord;
import com.comic.mapper.RechargeRecordMapper;
import com.comic.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordMapper rechargeMapper;

    @Override
    public List<RechargeRecord> getByUserId(Integer userId) {
        return rechargeMapper.findByUserId(userId);
    }

    @Override
    public void addRecord(Integer userId, Double amount) {
        rechargeMapper.insert(userId, amount);
    }

    @Override
    public Map<String, Object> getPage(int page, int size) {
        int offset = (page - 1) * size;
        List<RechargeRecord> list = rechargeMapper.findPage(offset, size);
        int total = rechargeMapper.countAll();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
    @Override
    public Map<String, Object> listAll(int page, int size) {
        int offset = (page - 1) * size;
        List<RechargeRecord> list = rechargeMapper.findPage(offset, size);
        int total = rechargeMapper.countAll();
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}