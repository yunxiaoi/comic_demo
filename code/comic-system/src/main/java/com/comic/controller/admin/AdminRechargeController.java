package com.comic.controller.admin;
//充值记录管理

import com.comic.common.Result;
import com.comic.entity.RechargeRecord;
import com.comic.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/recharge")
public class AdminRechargeController extends BaseAdminController {

    @Autowired
    private RechargeRecordService rechargeService;

    @GetMapping("/list")
    public Result<Map<String, Object>> listRecharges(@RequestParam(defaultValue = "1") Integer page,
                                                     @RequestParam(defaultValue = "20") Integer size,
                                                     HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(rechargeService.listAll(page, size));
    }
}