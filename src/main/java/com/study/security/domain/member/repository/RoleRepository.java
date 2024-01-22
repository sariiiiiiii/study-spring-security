package com.study.security.domain.member.repository;

import com.study.security.domain.member.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneById(Long id);

}
