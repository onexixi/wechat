package com.wechat.service;

import com.wechat.dto.WeChatCheckDTO;

/**
 * 微信
 */
public interface WeChatService {
    /**
     * 验证接口
     * @param weChatCheckDTO
     * @return
     */
    String weChatCheck(WeChatCheckDTO weChatCheckDTO);
}
