package org.zerock.nado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.nado.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String > {
}
