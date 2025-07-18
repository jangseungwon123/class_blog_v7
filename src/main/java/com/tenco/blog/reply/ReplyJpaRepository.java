package com.tenco.blog.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReplyJpaRepository extends JpaRepository<Reply, Long> {

    // 관리자 페이지
    // 오늘 달린 댓글 관리
}
