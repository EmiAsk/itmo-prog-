package se.ifmo.lab08.common.dto.response;

public interface BroadcastResponse<T> extends Response {
    T getData();
}
