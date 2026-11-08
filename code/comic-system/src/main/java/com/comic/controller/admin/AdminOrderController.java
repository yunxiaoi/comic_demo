package com.comic.controller.admin;
//订购管理

import com.comic.common.Result;
import com.comic.entity.UserOrder;
import com.comic.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends BaseAdminController {

    @Autowired
    private UserOrderService orderService;

    @GetMapping("/list")
    public Result<Map<String, Object>> listOrders(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size,
                                                  HttpServletRequest request) {
        checkAdmin(request);
        return Result.success(orderService.listAll(page, size));
    }
}