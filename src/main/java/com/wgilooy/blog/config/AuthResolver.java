package com.wgilooy.blog.config;

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

        String accessToken = webRequest.getHeader("Authorization");

        if(accessToken == null || accessToken.equals("")) {
            throw new UnAuthorized();
        }

        Token token = tokenRepository.findByAccessToken(accessToken)
            .orElseThrow(UnAuthorized:: new);


        return new UserSession(token.getUser().getId());
    }
    
}
