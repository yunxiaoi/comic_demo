package com.comic.controller.user;

import com.comic.common.Result;
import com.comic.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/upload")
public class UserUploadController {

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file,
                                      HttpServletRequest request) throws IOException {
        // 可选：获取当前登录用户ID用于记录（非必须）
        // Integer userId = (Integer) request.getAttribute("userId");
        String url = fileUploadUtils.upload(file);
        return Result.success(url);
    }
}