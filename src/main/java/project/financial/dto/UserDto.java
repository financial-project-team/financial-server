package project.financial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import project.financial.domain.User;

public class UserDto {

    @Getter
    public static class CreateUserRequest{
        private String name;
        private String uid;
        private String password;
    }
    @Getter
    public static class CreateUserResponse{
        private Long id;
        private String name;
        private String uid;
        private Long account;

        public CreateUserResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.uid = user.getUid();
            this.account = user.getAccount();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Result<T>{
        private T data;
    }
}
