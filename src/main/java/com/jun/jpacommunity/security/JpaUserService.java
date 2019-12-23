
package com.jun.jpacommunity.security;

import com.jun.jpacommunity.exception.UserNotFoundException;
import com.jun.jpacommunity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserDetailsService {

    @Autowired
    private  final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        JpaSecurityUser user = memberRepository.findById(username).filter(m -> m != null).map(m -> new JpaSecurityUser(m)).get();

        if(user == null){
            throw new UserNotFoundException("해당 계정이 존재하지 않습니다.");
        }

        return user;
    }
}

