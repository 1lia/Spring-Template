package com.app.api.logout.service;

import com.app.domain.member.entity.Member;
import com.app.domain.member.service.MemberService;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {
    private final MemberService memberService;
    private final TokenManager tokenManager;

    public void logout(String accessToken){
//      토큰 검증
        System.out.println(accessToken);
        tokenManager.validateToken(accessToken);
        System.out.println(accessToken);

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();

//      토큰 타입 확인
        if(!TokenType.isAccessToken(tokenType)){
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

//      refresh token 만료 처리
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        Member member = memberService.findMemberByMemberId(memberId);

        member.expireRefreshToken(LocalDateTime.now());



    }
}
