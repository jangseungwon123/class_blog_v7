package com.tenco.blog.reply;

import com.tenco.blog._core.errors.exception.Exception400;
import com.tenco.blog.board.Board;
import com.tenco.blog.user.User;
import jakarta.validation.constraints.*;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "게시글 정보가 필요합니다.")
        @Positive(message = "올바른 게시글 ID가 필요합니다.")
        private Long boardId;
        @NotEmpty(message = "댓글을 입력해주세요")
        @Size(min = 1, max = 500, message = "500자 이내로 작성해주세요")
        private String comment;

        /**
         * 입력 데이터 유효성 검증
         */

        /**
         * 보통 SAVE DTO에 toEntity 메서드를 만들게 된다
         * 멤버 변수에 없는 데이터가 필요할 때는
         * 외부에서 주입 받으면 된다.
         */
        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment.trim())
                    .user(sessionUser)
                    .board(board)
                    .build();
        }

    }


}
