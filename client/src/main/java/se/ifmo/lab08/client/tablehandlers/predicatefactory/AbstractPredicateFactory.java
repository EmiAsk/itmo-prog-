package se.ifmo.lab08.client.tablehandlers.predicatefactory;


import se.ifmo.lab08.client.tablehandlers.FlatColumnNames;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.implementations.*;

public class AbstractPredicateFactory {
    public PredicateFactory createFactory(FlatColumnNames name) {
        return switch (name) {
            case ID_COLUMN -> new IdColumnPredicateFactory();
            case NAME_COLUMN -> new NameColumnPredicateFactory();
            case COORDINATES_X_COLUMN -> new CoordinateXColumnPredicateFactory();
            case COORDINATES_Y_COLUMN -> new CoordinateYColumnPredicateFactory();
            case CREATION_DATE_COLUMN -> new CreationDateColumnPredicateFactory();
            case AREA_COLUMN -> new AreaColumnPredicateFactory();
            case NUMBER_OF_ROOMS -> new NumberOfRoomsPredicateFactory();
            case FURNISH_COLUMN -> new FurnishColumnPredicateFactory();
            case VIEW_COLUMN -> new ViewColumnPredicateFactory();
            case TRANSPORT_COLUMN -> new TransportColumnPredicateFactory();
            case HOUSE_NAME_COLUMN -> new HouseNameColumnPredicateFactory();
            case HOUSE_YEAR_COLUMN -> new HouseYearColumnPredicateFactory();
            case HOUSE_NUMBER_OF_FLATS_COLUMN -> new HouseNumberOfFlatsColumnPredicateFactory();
            case OWNER_USERNAME_COLUMN -> new OwnerNameColumnPredicateFactory();
        };
    }
}
