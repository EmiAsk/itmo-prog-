package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.entity.Flat;

import java.util.List;

public record CollectionResponse(List<Flat> flats) implements Response {
}
