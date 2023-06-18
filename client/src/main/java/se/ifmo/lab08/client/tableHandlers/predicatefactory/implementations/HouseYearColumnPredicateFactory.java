package se.ifmo.lab08.client.tableHandlers.predicatefactory.implementations;


import se.ifmo.lab08.client.tableHandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tableHandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.common.entity.Flat;

import java.util.Objects;
import java.util.function.Predicate;

public class HouseYearColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<Flat> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign) {
                case MORE_THAN -> createMorePredicate(Long.parseLong(value));
                case EQUALITY -> createEqualPredicate(Long.parseLong(value));
                case INEQUALITY -> createInequalPredicate(Long.parseLong(value));
                case LESS_THAN -> createLessPredicate(Long.parseLong(value));
            };
        } catch (NumberFormatException numberFormatException) {
            return value.isBlank() ? Objects::isNull : x -> false;
        }
    }

    private Predicate<Flat> createMorePredicate(long value) {
        return (band) -> band.getHouse().getYear() != null && band.getHouse().getYear() > value;
    }

    private Predicate<Flat> createLessPredicate(long value) {
        return (band) -> band.getHouse().getYear() != null && band.getHouse().getYear() < value;
    }

    private Predicate<Flat> createEqualPredicate(long value) {
        return (band) -> band.getHouse().getYear() != null && band.getHouse().getYear() == value;
    }

    private Predicate<Flat> createInequalPredicate(long value) {
        return (band) -> band.getHouse().getYear() != null && band.getHouse().getYear() != value;
    }
}
