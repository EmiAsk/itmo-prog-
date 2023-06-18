package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.entity.Flat;

public record UpdateModelResponse(Flat flat) implements BroadcastResponse<Flat> {
    public Flat getData() {
        return flat;
    }
}
