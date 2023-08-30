package com.app.external.oauth.service;

import com.app.domain.member.constant.MemberType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {
    private static Map<String , SocialLoginApiService> socialLoginApiService;

    public SocialLoginApiServiceFactory(Map<String , SocialLoginApiService> socialLoginApiService){
        this.socialLoginApiService = socialLoginApiService;
    }

    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType){
        String socialLoginApiServiceBeanName = "";

        if(MemberType.KAKAO.equals(memberType)){
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }
        return socialLoginApiService.get(socialLoginApiServiceBeanName);
    }
}
