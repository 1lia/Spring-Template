package com.app.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR , "001" , "business exception test"),

//  인증
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED , "A-001" , "토큰이 만료되었습니다"),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED , "A-002" , "해당 토큰은 유효한 토큰이 아닙니다"),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED , "A-003" , "AuthorizationHeader가 빈 값입니다"),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED , "A-004" , "인증 타입이 Bearer 타입이 아닙니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"A-005", "해당 refresh token은 존재하지 않습니다" ),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"A-006" ,"해당 refresh token은 만료되었습니다"),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED,"A-007" , "해당 토큰은 ACCESS TOKEN이 아닙니다"),
//   인가
    FORBIDDEN_ADMIN(HttpStatus.FORBIDDEN ,"A-008" , "관리자 Role이 아닙니다" ),


    //   회원
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST , "M-001" , "잘못된 회원 타입입니다. (MemberType : KAKAO)"),
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST , "M-002" , "이미 가입한 회원입니다."),
    MEMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST ,"M-003","해당 회원은 존재하지 않습니다" );


    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
