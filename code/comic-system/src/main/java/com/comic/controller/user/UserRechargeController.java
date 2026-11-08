package com.comic.controller.user;
//充值

import com.comic.common.Result;
import com.comic.dto.RechargeDTO;
import com.comic.entity.RechargeRecord;
import com.comic.service.RechargeRecordService;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/recharge")
public class UserRechargeController {

    @Autowired
    private RechargeRecordService rechargeService;
    @Autowired
    private UserService userService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @PostMapping("/do")
    public Result<?> recharge(@RequestBody RechargeDTO dto, HttpServletRequest request) {
        Integer userId = getUserId(request);
        Double amount = dto.getAmount();
        if (amount <= 0) {
            return Result.error("金额必须大于0");
        }
        // 模拟充值，实际应调用支付接口
        userService.addBalance(userId, amount);
        rechargeService.addRecord(userId, amount);  // 修改此处
        return Result.success("充值成功");
    }

    @GetMapping("/records")
    public Result<List<RechargeRecord>> getRecords(HttpServletRequest request) {
        return Result.success(rechargeService.getByUserId(getUserId(request)));
    }
}