package com.study.security.domain.member.entity;

import com.study.security.api.service.request.SignUpServiceRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String name;

    /**
     * Member 와 Role 을 N:N(다대다) 관계로 매핑
     * Member 엔티티의 변경 사항이 Role 엔티티에도 적용이 된다
     *  - Member 엔티티가 수정되면 해당하는 Role 엔티티도 수정된다
     * 다대다 관계를 위한 연결 테이블을 지정
     *  - name = "member_roles" 연결테이블의 이름을 "member_roles"로 지정
     *  - joinColumns = @JoinColumn(name = "member_id") Member 엔티티의 기본 키가 참조되는 열의 이름을 "member_id"로 지정
     *  - inverseJoinColumns = @JoinColumn(name = "role_id") Role 엔티티의 기본 키가 참조되는 열의 이름을 role_id로 지정
     *
     * CascadeType.MERGE : 엔티티의 상태가 변경되었을 때 해당 변경이 연관된 엔티티에도 병합되는 설정
     * CascadeType.PERSIST : 새로운 엔티티가 영속성 컨텍스트에 추가되면 해당 변경이 연관된 엔티티에도 적용되도록 설정
     * CascadeType.REFRESH : 엔티티를 다시 읽어와서 현재 상태로 갱신할 때 해당 변경이 연관된 엔티티에도 적용되도록 설정
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Member(SignUpServiceRequest request, Set<Role> rolesSet) {
        this.userId = request.getUserId();
        this.password = request.getPassword();
        this.name = request.getName();
        this.roles = rolesSet;
    }
}
