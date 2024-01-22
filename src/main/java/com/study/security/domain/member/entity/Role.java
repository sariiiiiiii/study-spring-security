package com.study.security.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /**
     * @ManyToMany : Role 클래스와 Member 클래스 간의 다대다(N-N) 관계를 나타냄
     * mappedBy = "roles" 양방향 매핑에서 상대편 클래스(Member)에서 관계를 관리하고 있는 필드(roles)를 지정
     */
    @ManyToMany(mappedBy = "roles")
    private Set<Member> members;

}
