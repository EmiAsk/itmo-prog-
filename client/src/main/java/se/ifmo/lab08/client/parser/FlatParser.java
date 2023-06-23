package se.ifmo.lab08.client.parser;

import se.ifmo.lab08.client.resourcebundles.enums.FlatFormElements;
import se.ifmo.lab08.common.entity.*;
import se.ifmo.lab08.common.util.Printer;

import java.util.Arrays;
import java.util.Scanner;

public class FlatParser extends DefaultParser {

    public FlatParser(Scanner scanner, Printer printer) {
        super(scanner, printer);
    }

    public House parseHouse() {
        // House Name
        String name;

        while (!House.validateName(name = parseString(FlatFormElements.HOUSE_NAME_LABEL.toString(), FlatFormElements.FLAT_NAME_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        // House year
        Long year;
        while (!House.validateYear(year = parseLong(FlatFormElements.HOUSE_YEAR_LABEL.toString(), FlatFormElements.HOUSE_YEAR_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        // House numberOfFlatsOnFloor
        Integer flatsNumber;
        while (!House.validateFlatsNumber(flatsNumber = parseInt(FlatFormElements.HOUSE_NUMBER_OF_FLATS_LABEL.toString(), FlatFormElements.HOUSE_NUMBER_OF_FLATS_TEXT_FIELD.toString()))) {
            print(FlatFormElements.INVALID_VALUE.toString());
        }
        return new House().setName(name)
                .setYear(year)
                .setNumberOfFlatsOnFloor(flatsNumber);
    }

    public Coordinates parseCoordinates() {
        // Coordinate X
        Long x;
        while (!Coordinates.validateX(x = parseLong(FlatFormElements.COORDINATE_X_LABEL.toString(), FlatFormElements.COORDINATE_X_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        // Coordinate Y
        Float y;
        while (!Coordinates.validateY(y = parseFloat(FlatFormElements.COORDINATE_Y_LABEL.toString(), FlatFormElements.COORDINATE_Y_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        return new Coordinates(x, y);
    }

    public Flat parseFlat() {
        // Flat Name
        String name;
        while (!Flat.validateName(name = parseString(FlatFormElements.FLAT_NAME_LABEL.toString(), FlatFormElements.FLAT_NAME_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        // Flat Coordinates
        Coordinates coordinates = parseCoordinates();
        // Flat Area
        Long area;
        while (!Flat.validateArea(area = parseLong(FlatFormElements.AREA_LABEL.toString(), FlatFormElements.AREA_TEXT_FIELD.toString())))
            print(FlatFormElements.INVALID_VALUE.toString());
        // Flat NumberOfRooms
        Long numberOfRooms;
        while (!Flat.validateNumberOfRooms(numberOfRooms = parseLong(FlatFormElements.NUMBER_OF_ROOMS_LABEL.toString(), FlatFormElements.NUMBER_OF_ROOMS_TEXT_FIELD.toString()))) {
            print(FlatFormElements.INVALID_VALUE.toString());
        }
        // Flat Furnish
        Furnish furnish;
        String furnishValues = Arrays.asList(Furnish.values()).toString();
        while (!Flat.validateFurnish(furnish = parseEnum(Furnish.class, FlatFormElements.FURNISH_LABEL.toString() + " " + furnishValues, ""))) {
            print(FlatFormElements.INVALID_VALUE.toString());
        }
        // Flat View
        View view;
        String viewValues = Arrays.asList(View.values()).toString();
        while (!Flat.validateView(view = parseEnum(View.class, FlatFormElements.VIEW_LABEL.toString() + " " + viewValues, ""))) {
            print(FlatFormElements.INVALID_VALUE.toString());
        }
        // Flat Transport
        Transport transport;
        String transportValues = Arrays.asList(Transport.values()).toString();
        while (!Flat.validateTransport(transport = parseEnum(Transport.class, FlatFormElements.TRANSPORT_LABEL.toString() + " " + transportValues, ""))) {
            print(FlatFormElements.INVALID_VALUE.toString());
        }
        // Flat House
        House house = parseHouse();

        return new Flat().setName(name)
                .setCoordinates(coordinates)
                .setArea(area)
                .setNumberOfRooms(numberOfRooms)
                .setFurnish(furnish)
                .setView(view)
                .setTransport(transport)
                .setHouse(house);
    }
}
