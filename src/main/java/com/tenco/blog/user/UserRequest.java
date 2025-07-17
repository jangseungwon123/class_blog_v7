package com.tenco.blog.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        @NotEmpty(message = "사용자명을 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자/2~20자 이내로 작성해주세요")
        private String username;
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용X
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String password;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String email;

        // JoinDTO 를 User Object 변환 하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .build();
        }

        // 회원가입시 유효성 검증 메서드
        public void validate() {

            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명은 필수야");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수야");
            }
            // 간단한 이메일 형식 검증 (정규화 표현식)
            if (email.contains("@") == false) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
            }
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {

        @NotEmpty(message = "사용자명을 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$",
                message = "영문/숫자/2~20자 이내로 작성해주세요")
        private String username;

        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용X
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String password;
    }

    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용X
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String password;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String email;
    }


}
