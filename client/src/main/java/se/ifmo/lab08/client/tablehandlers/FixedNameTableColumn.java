package se.ifmo.lab08.client.tablehandlers;

import javafx.scene.control.TableColumn;

public class FixedNameTableColumn<S, T> extends TableColumn<S, T> {
    private final FlatColumnNames fixedName;

    public FixedNameTableColumn(FlatColumnNames columnFixedName) {
        super(columnFixedName.toString());
        this.fixedName = columnFixedName;
    }

    public FlatColumnNames getFixedName() {
        return fixedName;
    }
}
