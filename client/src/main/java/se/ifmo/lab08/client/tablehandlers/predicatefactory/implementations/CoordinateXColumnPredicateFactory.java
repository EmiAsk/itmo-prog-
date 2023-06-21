package se.ifmo.lab08.client.tablehandlers.predicatefactory.implementations;


import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.common.entity.Flat;

import java.util.Objects;
import java.util.function.Predicate;

public class CoordinateXColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<Flat> createPredicate(FilterSigns filterSign, String value) {
        try {
            return switch (filterSign) {
                case MORE_THAN -> createMorePredicate(Integer.parseInt(value));
                case EQUALITY -> createEqualPredicate(Integer.parseInt(value));
                case INEQUALITY -> createInequalPredicate(Integer.parseInt(value));
                case LESS_THAN -> createLessPredicate(Integer.parseInt(value));
            };
        } catch (NumberFormatException numberFormatException) {
            return value == null ? Objects::isNull : x -> false;
        }
    }

    private Predicate<Flat> createMorePredicate(int value) {
        return (band) -> band.getCoordinates().getX() > value;
    }

    private Predicate<Flat> createLessPredicate(int value) {
        return (band) -> band.getCoordinates().getX() < value;
    }

    private Predicate<Flat> createEqualPredicate(int value) {
        return (band) -> band.getCoordinates().getX() == value;
    }

    private Predicate<Flat> createInequalPredicate(int value) {
        return (band) -> band.getCoordinates().getX() != value;
    }
}
