package com.study.security.api.controller.request;

import com.study.security.api.service.request.SignUpServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    private String userId;

    private String password;

    private String name;

    public SignUpServiceRequest toService() {
        return new SignUpServiceRequest(this);
    }

}
