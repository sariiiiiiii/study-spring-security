package com.study.security.api.service;

import com.study.security.domain.member.entity.Member;
import com.study.security.domain.member.entity.Role;
import com.study.security.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Member member = memberRepository.findByUserId(userId); // JPA 를 이용해서 Member 조회시 Role 같이 조회

        if (member != null) {
            for (Role role : member.getRoles()) {
                // 로그인 시 user.getRoles() 메소드를 호출함으로써 사용자의 DB에 저장되어 있는 역할을 조회하여 부여한다
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            // UserDetails 인터페이스를 구현한 User 객체를 생성하여 반환, 이 객체는 Spring Security 가 인증 및 권한 부여를 수행하는데 필요한 정보를 담고 있음
            return new User(member.getUserId(), member.getPassword(), grantedAuthorities);

        } else {
            throw new UsernameNotFoundException("can not find User : " + userId);
        }
    }

}
