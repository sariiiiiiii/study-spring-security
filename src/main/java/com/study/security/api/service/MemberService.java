package com.study.security.api.service;

import com.study.security.api.service.request.SignUpServiceRequest;
import com.study.security.domain.member.entity.Role;
import com.study.security.domain.member.repository.MemberRepository;
import com.study.security.domain.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(SignUpServiceRequest request) {
        request.encodedPassword(bCryptPasswordEncoder);
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(roleRepository.findOneById(2L));
        memberRepository.save(request.toEntity(rolesSet));
    }

}
