package com.wechat.controller;

import com.wechat.dto.WeChatCheckDTO;
import com.wechat.service.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/weChat")
@Slf4j
@Api(value = "weChat", description = "微信公众号")
public class WeChatController {

    @Autowired
    WeChatService weChatService;

    @ApiOperation(value = "微信验证get消息", tags = {"微信验证接口"})
    @RequestMapping(value = "/weChatMsg", method = RequestMethod.GET)
    public String weChatAccessToken(@RequestParam String signature,
                                    @RequestParam String timestamp,
                                    @RequestParam String nonce,
                                    @RequestParam String echostr) {
        WeChatCheckDTO weChatCheckDTO = new WeChatCheckDTO();
        weChatCheckDTO.setSignature(signature);
        weChatCheckDTO.setTimestamp(timestamp);
        weChatCheckDTO.setNonce(nonce);
        weChatCheckDTO.setEchostr(echostr);
        log.info("---微信验证+"+"Signature:"+weChatCheckDTO.getSignature()+"Timestamp:"+weChatCheckDTO.getTimestamp()+"Nonce:"+weChatCheckDTO.getNonce());
        return weChatService.weChatCheck(weChatCheckDTO);

    }

    @ApiOperation(value = "接受Jenkins Webhook请求", tags = {"接受Jenkins Webhook请求"})
    @PostMapping(value = "/weChatMsg")
    public String weChatPostMsg(@RequestBody String context) {
        log.info(context);
//         myRecordService.weChatPostMsg(context);
        return null;
    }

}
