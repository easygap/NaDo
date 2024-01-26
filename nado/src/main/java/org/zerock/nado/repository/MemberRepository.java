package org.zerock.nado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.nado.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String > {
    Boolean existsByid(String id);

    // id를 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    Member findByid(String id);
}
