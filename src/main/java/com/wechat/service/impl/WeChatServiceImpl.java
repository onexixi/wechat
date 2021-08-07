package com.wechat.service.impl;

import com.wechat.config.WeChatConfig;
import com.wechat.dto.WeChatCheckDTO;
import com.wechat.service.WeChatService;
import com.wechat.util.CheckUtil;
import com.xxl.job.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 微信
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public String weChatCheck(WeChatCheckDTO weChatCheckDTO) {
        String token = weChatConfig.getToken ();
        if (CheckUtil.checkSignature (token, weChatCheckDTO.getSignature (), weChatCheckDTO.getTimestamp (), weChatCheckDTO.getNonce ())) {
            log.warn ("---微信验证通过+" + "Signature:" + weChatCheckDTO.getSignature () + "Timestamp:" + weChatCheckDTO.getTimestamp () + "Nonce:" + weChatCheckDTO.getNonce ());
            return weChatCheckDTO.getEchostr ();
        }
        log.warn ("---微信验证失败");
        return "";

    }
    @Override
    public String taskMethod() {
        log.warn ("---正常调度---");
        log.warn ("---正常调度---"+ DateUtil.formatDateTime(new Date()));
        return "Success";
    }
}
