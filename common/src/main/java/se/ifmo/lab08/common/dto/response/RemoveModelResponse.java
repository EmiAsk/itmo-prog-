package se.ifmo.lab08.common.dto.response;

import java.util.List;

public record RemoveModelResponse(List<Long> ids) implements BroadcastResponse<List<Long>> {
    public List<Long> getData() {
        return ids;
    }
}
