package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.entity.User;

import java.util.List;

public record UserCollectionResponse(List<User> users) implements Response {
}
