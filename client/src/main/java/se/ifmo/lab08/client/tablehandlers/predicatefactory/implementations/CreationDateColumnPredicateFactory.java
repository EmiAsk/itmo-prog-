package se.ifmo.lab08.client.tablehandlers.predicatefactory.implementations;

import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.common.entity.Flat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.function.Predicate;

public class CreationDateColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<Flat> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign) {
                case MORE_THAN -> createMorePredicate(LocalDate.parse(value));
                case EQUALITY -> createEqualPredicate(LocalDate.parse(value));
                case INEQUALITY -> createInequalPredicate(LocalDate.parse(value));
                case LESS_THAN -> createLessPredicate(LocalDate.parse(value));
            };
        } catch (DateTimeParseException dateTimeParseException) {
            return value == null ? Objects::isNull : x -> false;
        }
    }

    private Predicate<Flat> createMorePredicate(LocalDate value) {
        return (band) -> band.getCreatedAt().toLocalDate().isAfter(value);
    }

    private Predicate<Flat> createLessPredicate(LocalDate value) {
        return (band) -> band.getCreatedAt().toLocalDate().isBefore(value);
    }

    private Predicate<Flat> createEqualPredicate(LocalDate value) {
        return (band) -> band.getCreatedAt().toLocalDate().equals(value);
    }

    private Predicate<Flat> createInequalPredicate(LocalDate value) {
        return (band) -> !band.getCreatedAt().toLocalDate().equals(value);
    }
}
