package se.ifmo.lab08.common.dto;

import java.io.Serializable;

public record CommandDTO(
        String name,
        String description,
        Class<?>[] args,
        boolean modelRequired
) implements Serializable {
}
