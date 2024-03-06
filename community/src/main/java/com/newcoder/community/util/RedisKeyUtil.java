package com.newcoder.community.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    //redis:帖子
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    //redis:用户
    private static final String PREFIX_USER_LIKE = "like:user";
    //redis:用户关注的实体
    private static final String PREFIX_FOLLOWEE = "followee";
    //redis:实体的粉丝
    private static final String PREFIX_FOLLOWER = "follower";
    //某个实体的赞
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
    //某个用户关注的实体
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + entityType;
    }
    //某个实体拥有的粉丝
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + entityId;
    }

}
