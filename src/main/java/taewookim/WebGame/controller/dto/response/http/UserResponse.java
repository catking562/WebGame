package taewookim.WebGame.controller.dto.response.http;

import taewookim.WebGame.entity.User;

public record UserResponse(Long id, String username) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getUserName());
    }

}
