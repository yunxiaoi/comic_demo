package com.comic.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer targetType;
    private Integer targetId;
    private String content;
    private LocalDateTime createTime;
    private String username;      // 评论者的用户名（关联查询）
    private String targetTitle;   // 被评论对象的标题（漫画标题或帖子标题）
}