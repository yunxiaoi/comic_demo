// ForumPostMapper.java
package com.comic.mapper;

import com.comic.entity.ForumPost;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ForumPostMapper {

    @Select("SELECT * FROM forum_post WHERE id = #{id}")
    ForumPost findById(Integer id);

    @Insert("INSERT INTO forum_post (user_id, category_id, title, content, status) VALUES (#{userId}, #{categoryId}, #{title}, #{content}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ForumPost post);

    @Update("UPDATE forum_post SET category_id = #{categoryId}, title = #{title}, content = #{content}, status = #{status} WHERE id = #{id}")
    int update(ForumPost post);

    @Delete("DELETE FROM forum_post WHERE id = #{id}")
    int deleteById(Integer id);

    @Update("UPDATE forum_post SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Update("UPDATE forum_post SET views = views + 1 WHERE id = #{id}")
    void incrementViews(Integer id);

    List<ForumPost> findByStatus(@Param("status") Integer status);

    List<ForumPost> findAll();

    @Select("SELECT * FROM forum_post WHERE user_id = #{userId} ORDER BY id DESC")
    List<ForumPost> findByUserId(Integer userId);

    // 已审核帖子分页
    List<ForumPost> findApproved(@Param("categoryId") Integer categoryId,
                                 @Param("offset") int offset,
                                 @Param("size") int size);

    int countApproved(@Param("categoryId") Integer categoryId);

    // 统计
    @Select("SELECT COUNT(*) FROM forum_post")
    int countAll();

    @Select("SELECT pc.name, COUNT(fp.id) as count FROM post_category pc LEFT JOIN forum_post fp ON pc.id = fp.category_id GROUP BY pc.id")
    List<Map<String, Object>> countByCategory();

    List<ForumPost> findPendingPosts(@Param("offset") int offset, @Param("size") int size);
    int countPending();
}