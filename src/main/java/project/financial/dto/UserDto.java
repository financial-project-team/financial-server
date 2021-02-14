package project.financial.dto;

import lombok.Data;
import lombok.Getter;
import project.financial.domain.User;

public class UserDto {

    @Getter
    public static class CreateUserRequest{
        private String name;
        private String email;
        private String password;
    }
    @Getter
    public static class CreateUserResponse{
        private Long id;
        private String name;
        private String email;
        private Integer account;

        public CreateUserResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.account = user.getAccount();
        }
    }
    @Data
    public static class SignRequest{
        private String email;
        private String password;
    }

}
