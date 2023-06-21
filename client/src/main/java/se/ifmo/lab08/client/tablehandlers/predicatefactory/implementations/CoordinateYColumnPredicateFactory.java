package se.ifmo.lab08.client.tablehandlers.predicatefactory.implementations;


import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.common.entity.Flat;

import java.util.Objects;
import java.util.function.Predicate;

public class CoordinateYColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<Flat> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign) {
                case MORE_THAN -> createMorePredicate(Double.parseDouble(value));
                case EQUALITY -> createEqualPredicate(Double.parseDouble(value));
                case INEQUALITY -> createInequalPredicate(Double.parseDouble(value));
                case LESS_THAN -> createLessPredicate(Double.parseDouble(value));
            };
        } catch (NumberFormatException numberFormatException) {
            return value == null ? Objects::isNull : x -> false;
        }
    }

    private Predicate<Flat> createMorePredicate(double value) {
        return (band) -> band.getCoordinates().getY() > value;
    }

    private Predicate<Flat> createLessPredicate(double value) {
        return (band) -> band.getCoordinates().getY() < value;
    }

    private Predicate<Flat> createEqualPredicate(double value) {
        return (band) -> band.getCoordinates().getY() == value;
    }

    private Predicate<Flat> createInequalPredicate(double value) {
        return (band) -> band.getCoordinates().getY() != value;
    }
}
