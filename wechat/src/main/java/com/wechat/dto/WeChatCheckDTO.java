package com.wechat.dto;
import lombok.Data;

/**
 * @author yangxixi
 * @date
 */
@Data
public class WeChatCheckDTO {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

}
