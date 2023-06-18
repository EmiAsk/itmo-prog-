package se.ifmo.lab08.client.tableHandlers;

import javafx.scene.control.TableColumn;

public class FixedNameTableColumn<S, T> extends TableColumn {
    private ColumnNames fixedName;

    public FixedNameTableColumn(ColumnNames columnFixedName) {
        super(columnFixedName.toString());
        this.fixedName = columnFixedName;
    }

    public ColumnNames getFixedName() {
        return fixedName;
    }
}