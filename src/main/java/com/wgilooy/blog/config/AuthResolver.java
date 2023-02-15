package com.wgilooy.blog.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wgilooy.blog.config.data.UserSession;
import com.wgilooy.blog.domain.Token;
import com.wgilooy.blog.exception.UnAuthorized;
import com.wgilooy.blog.repositroy.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final TokenRepository tokenRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        // String accessToken = webRequest.getHeader("Authorization");

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            log.error("사용자 인증 request null");
            throw new UnAuthorized();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) {
            log.error("사용자 인증 cookie null");
            throw new UnAuthorized();
        }

        // if(accessToken == null || accessToken.equals("")) {
        // throw new UnAuthorized();
        // }

        String accessToken = cookies[0].getValue();

        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(UnAuthorized::new);

        return new UserSession(token.getUser().getId());
    }

}
