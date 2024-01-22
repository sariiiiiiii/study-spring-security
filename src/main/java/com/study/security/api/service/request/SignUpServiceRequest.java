package com.study.security.api.service.request;

import com.study.security.api.controller.request.SignUpRequest;
import com.study.security.domain.member.entity.Member;
import com.study.security.domain.member.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Getter
@NoArgsConstructor
public class SignUpServiceRequest {

    private String userId;

    private String password;

    private String name;

    public SignUpServiceRequest(SignUpRequest request) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
        this.name = request.getName();
    }

    public void encodedPassword(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.password = bCryptPasswordEncoder.encode(this.password);
    }

    public Member toEntity(Set<Role> rolesSet) {
        return new Member(this, rolesSet);
    }

}
