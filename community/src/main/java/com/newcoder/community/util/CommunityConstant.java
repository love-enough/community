package com.newcoder.community.util;

public  interface CommunityConstant {
    /**
     * @Description 激活成功
     * @author GuoZihan
     * @date 14:18 2024/3/3
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * @Description 重复激活
     * @author GuoZihan
     * @date 14:18 2024/3/3
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * @Description 激活失败
     * @author GuoZihan
     * @date 14:19 2024/3/3
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * @Description 默认状态的登录凭证的超时时间
     * @author GuoZihan
     * @date 16:43 2024/3/3
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    /**
     * @Description 记住状态的登陆凭证超时时间
     * @author GuoZihan
     * @date 16:44 2024/3/3
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}
