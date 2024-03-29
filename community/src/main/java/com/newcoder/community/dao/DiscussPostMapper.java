package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * @Description 增加帖子
     * @author GuoZihan
     * @date 11:40 2024/3/4
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * @Description 根据id搜索帖子
     * @author GuoZihan
     * @date 14:26 2024/3/4
     */
    DiscussPost selectDiscussPostById(int id);

    /**
     * @Description 更新评论数量
     * @author GuoZihan
     * @date 19:12 2024/3/4
     */
    int updateCommentCount(int id,int commentCount);

}
