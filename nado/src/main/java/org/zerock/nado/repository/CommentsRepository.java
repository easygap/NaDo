package org.zerock.nado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.nado.entity.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
