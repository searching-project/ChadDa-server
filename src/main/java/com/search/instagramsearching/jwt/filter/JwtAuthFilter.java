package com.search.instagramsearching.jwt.filter;

import com.search.instagramsearching.exception.ErrorCode;
import com.search.instagramsearching.jwt.util.JwtUtil;
import com.search.instagramsearching.jwt.util.TokenProperties;
import com.search.instagramsearching.security.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Header에서 access token 받기
        String jwtHeader = request.getHeader(TokenProperties.AUTH_HEADER);
        String requestUri = request.getRequestURI();

        if(jwtHeader != null){

            if(!jwtHeader.startsWith(TokenProperties.TOKEN_TYPE)){
                jwtUtil.exceptionResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
                return;
            }

            String accessToken = jwtHeader.replace(TokenProperties.TOKEN_TYPE,"");

            String validate = jwtUtil.validateToken(accessToken);

            switch (validate) {
                case TokenProperties.EXPIRED:
                    jwtUtil.exceptionResponse(response, ErrorCode.EXPIRED_ACCESS_TOKEN);
                    return;
                case TokenProperties.INVALID:
                    jwtUtil.exceptionResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
                    return;
                case TokenProperties.VALID:
                    // JWT 로부터 권한 값 가져오기
                    String username = jwtUtil.getUsernameFromToken(accessToken);

                    if (username == null) {
                        jwtUtil.exceptionResponse(response, ErrorCode.INVALID_ACCESS_TOKEN);
                        return;
                    }

                    // JWT 검증 성공 -> 권한 값으로부터 유저를 가져온다
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (userDetails == null) {
                        jwtUtil.exceptionResponse(response, ErrorCode.USER_NOT_FOUND);
                        return;
                    }

                    // 모든 예외처리 통과 -> 인증객체 생성, 권한부여
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    chain.doFilter(request, response);
                    break;
            }

        }else{
            // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
            // auth가 포함되지 않은 uri는 인가를 거치지 않고 다음 필터로 넘어간다.
            chain.doFilter(request,response);
        }
    }

}
