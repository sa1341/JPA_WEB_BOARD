package com.jun.jpacommunity.security;

import com.jun.jpacommunity.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


// 젠장... 이 커스터마이징 한 인증제공자는... 실제로 인증매니저 빌더에 의해 인증관리자로..등록이안되어있음.. Dao 인증 제공자를 현재 사용중임... 내일 한번 다시 찾아보기.

@Component
@RequiredArgsConstructor
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    private final JpaUserService jpaUserService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        UserDetails userDetails = null;

        try {
            userDetails = jpaUserService.loadUserByUsername(username);

        }catch (UserNotFoundException e){
            System.out.println(e.getMessage());
        }catch (BadCredentialsException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
