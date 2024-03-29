package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {
    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId , Role role){
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpirationTime = createRefreshTokenExpirationTime();
        String accessToken = createAccessToken(memberId , role , accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId , refreshTokenExpirationTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpirationTime)
                .build();
    }

    public Date createAccessTokenExpireTime(){
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpirationTime(){
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long memberId , Role role , Date expirationTime){
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name()) //토큰 제목
                .setIssuedAt(new Date())             //토큰발급시간
                .setExpiration(expirationTime)       //토큰만료시간
                .claim("memberId" , memberId)  //회원 아이디
                .claim("role" , role)          //회원 role
                .signWith(SignatureAlgorithm.HS512 , tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ" , "JWT")
                .compact();
        return accessToken;
    }

    public String createRefreshToken(Long memberId , Date expirationTime){
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name()) //토큰 제목
                .setIssuedAt(new Date())             //토큰발급시간
                .setExpiration(expirationTime)       //토큰만료시간
                .claim("memberId" , memberId)  //회원 아이디
                .signWith(SignatureAlgorithm.HS512 , tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ" , "JWT")
                .compact();
        return refreshToken;
    }

    public void validateToken(String token){
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e){
            log.info("token 만료" , e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e){
            log.info("유효하지 않은 token" , e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public Claims getTokenClaims(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e){
            log.info("유효하지 않은 token" , e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }
}
