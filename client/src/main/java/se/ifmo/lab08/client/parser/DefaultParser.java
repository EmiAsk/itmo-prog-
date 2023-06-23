package se.ifmo.lab08.client.parser;

import se.ifmo.lab08.client.resourcebundles.enums.FlatFormElements;
import se.ifmo.lab08.common.exception.InterruptCommandException;
import se.ifmo.lab08.common.parser.Parser;
import se.ifmo.lab08.common.util.Printer;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DefaultParser implements Parser {
    private final Scanner input;
    private final Printer printer;

    public DefaultParser(Scanner scanner, Printer printer) {
        this.input = scanner;
        this.printer = printer;
    }

    public String parseString(String name, String descr) throws NoSuchElementException {
        if (descr == null || descr.equals("")) {
            printer.printf("\n%s:\n", name);
        } else {
            printer.printf("\n%s (%s):\n", name, descr);
        }
        String line = input.nextLine().strip();

        if (line.equals("exit")) {
            throw new InterruptCommandException();
        }
        return line.equals("") ? null : line;
    }

    public Integer parseInt(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Integer.parseInt(line) : null;
            } catch (NumberFormatException exception) {
                printer.print(FlatFormElements.INVALID_VALUE.toString());
            }
        }
    }

    public Long parseLong(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Long.parseLong(line) : null;
            } catch (NumberFormatException exception) {
                printer.print(FlatFormElements.INVALID_VALUE.toString());
            }
        }
    }

    public Float parseFloat(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Float.parseFloat(line) : null;
            } catch (NumberFormatException exception) {
                printer.print(FlatFormElements.INVALID_VALUE.toString());
            }
        }
    }

    public Double parseDouble(String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Double.parseDouble(line) : null;
            } catch (NumberFormatException exception) {
                printer.print(FlatFormElements.INVALID_VALUE.toString());
            }
        }
    }

    public <T extends Enum<T>> T parseEnum(Class<T> enumType, String name, String descr) {
        while (true) {
            try {
                String line = parseString(name, descr);
                return (line != null) ? Enum.valueOf(enumType, line) : null;
            } catch (NullPointerException | IllegalArgumentException exception) {
                printer.print(FlatFormElements.INVALID_VALUE.toString());
            }
        }
    }

    public void print(String text) {
        printer.print(text);
    }

}
