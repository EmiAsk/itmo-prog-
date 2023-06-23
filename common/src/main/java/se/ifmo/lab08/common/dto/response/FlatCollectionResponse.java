package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.entity.Flat;

import java.util.List;

public record FlatCollectionResponse(List<Flat> flats) implements BroadcastResponse<List<Flat>> {
    @Override
    public List<Flat> getData() {
        return flats;
    }
}
