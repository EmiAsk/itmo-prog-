package se.ifmo.lab08.client.tablehandlers.predicatefactory;

import se.ifmo.lab08.common.entity.Flat;

import java.util.function.Predicate;

public abstract class PredicateFactory {
    public abstract Predicate<Flat> createPredicate(FilterSigns filterSign, String value);
}
