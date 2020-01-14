package com.yl.sell.config;


import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;

public class WechatOpenConfig {
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService(){
        WxMpServiceImpl wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage wxOpenInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxOpenInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxOpenInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        return wxOpenInMemoryConfigStorage;
    }

}
