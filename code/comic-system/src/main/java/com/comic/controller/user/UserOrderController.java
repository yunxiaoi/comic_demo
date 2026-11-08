package com.comic.controller.user;
//购买记录

import com.comic.common.Result;
import com.comic.entity.Comic;
import com.comic.entity.UserOrder;
import com.comic.service.ComicService;
import com.comic.service.UserOrderService;
import com.comic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user/order")
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;
    @Autowired
    private ComicService comicService;
    @Autowired
    private UserService userService;

    private Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    @PostMapping("/buy")
    public Result<?> buyComic(@RequestParam Integer comicId, HttpServletRequest request) {
        Integer userId = getUserId(request);
        Comic comic = comicService.getById(comicId);
        if (comic == null) {
            return Result.error("漫画不存在");
        }
        if (comic.getIsPaid() == 0) {
            return Result.error("该漫画为免费，无需购买");
        }
        // 检查是否已购买
        if (orderService.getByUserAndComic(userId, comicId) != null) {
            return Result.error("您已购买过该漫画");
        }
        boolean success = orderService.purchase(userId, comicId, comic.getPrice());
        if (success) {
            return Result.success("购买成功");
        } else {
            return Result.error("余额不足");
        }
    }

    @GetMapping("/list")
    public Result<List<UserOrder>> getOrders(HttpServletRequest request) {
        Integer userId = getUserId(request);
        return Result.success(orderService.getByUserId(userId));
    }

    @GetMapping("/check")
    public Result<Boolean> checkPurchased(@RequestParam Integer comicId, HttpServletRequest request) {
        return Result.success(orderService.getByUserAndComic(getUserId(request), comicId) != null);
    }

}