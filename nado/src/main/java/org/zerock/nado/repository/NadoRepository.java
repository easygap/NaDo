package org.zerock.nado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.nado.entity.Nado;

public interface NadoRepository extends JpaRepository<Nado, Long>, QuerydslPredicateExecutor<Nado> {
}
