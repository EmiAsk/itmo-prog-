package se.ifmo.lab08.client.tablehandlers.predicatefactory.implementations;


import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.common.entity.Flat;

import java.util.function.Predicate;

public class HouseNameColumnPredicateFactory extends PredicateFactory {
    @Override
    public Predicate<Flat> createPredicate(FilterSigns filterSign, String value) {
        return switch (filterSign) {
            case MORE_THAN -> createMorePredicate(value);
            case EQUALITY -> createEqualPredicate(value);
            case INEQUALITY -> createInequalPredicate(value);
            case LESS_THAN -> createLessPredicate(value);
        };
    }

    private Predicate<Flat> createMorePredicate(String value) {
        return (band) -> band.getName().compareTo(value) > 0;
    }

    private Predicate<Flat> createLessPredicate(String value) {
        return (band) -> band.getName().compareTo(value) < 0;
    }

    private Predicate<Flat> createEqualPredicate(String value) {
        return (band) -> band.getName().equals(value);
    }

    private Predicate<Flat> createInequalPredicate(String value) {
        return (band) -> !band.getName().equals(value);
    }
}
