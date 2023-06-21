package se.ifmo.lab08.client.util;

import se.ifmo.lab08.common.util.Printer;

public class BufferPrinter implements Printer {

    private final StringBuilder builder;

    public BufferPrinter(StringBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void print(String text) {
        builder.append(text);
        builder.append("\n");
    }

    @Override
    public void printf(String format, Object... args) {
        builder.append(format.formatted(args));
    }
}
