package se.ifmo.lab08.common.dto.response;

public record RemoveUserResponse(int id) implements BroadcastResponse<Integer> {
    @Override
    public Integer getData() {
        return id;
    }
}
