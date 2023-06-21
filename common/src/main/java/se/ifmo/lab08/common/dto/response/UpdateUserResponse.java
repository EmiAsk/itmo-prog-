package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.entity.User;

public record UpdateUserResponse(User user) implements BroadcastResponse<User> {
    @Override
    public User getData() {
        return user;
    }
}
