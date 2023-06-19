package se.ifmo.lab08.client.tableHandlers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.entity.Transport;
import se.ifmo.lab08.common.entity.View;

public class TableColumnsInitializer {
    private final TableView<Flat> tableView;

    public TableColumnsInitializer(TableView<Flat> tableView) {
        this.tableView = tableView;
    }

    public void initializeColumns() {
        TableColumn<Flat, Long> idColumn = new FixedNameTableColumn<>(ColumnNames.ID_COLUMN);
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn<Flat, String> nameColumn = new FixedNameTableColumn<>(ColumnNames.NAME_COLUMN);
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        TableColumn<Flat, Long> coordinateXColumn = new FixedNameTableColumn<>(ColumnNames.COORDINATES_X_COLUMN);
        coordinateXColumn.setCellValueFactory(cellValue -> new SimpleLongProperty(cellValue.getValue().getCoordinates().getX()).asObject());
        TableColumn<Flat, Float> coordinateYColumn = new FixedNameTableColumn<>(ColumnNames.COORDINATES_Y_COLUMN);
        coordinateYColumn.setCellValueFactory(cellValue -> new SimpleFloatProperty(cellValue.getValue().getCoordinates().getY()).asObject());
        TableColumn<Flat, String> creationDateColumn = new FixedNameTableColumn<>(ColumnNames.CREATION_DATE_COLUMN);
//        creationDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getCreatedAt().withZoneSameInstant(MainFormController.getCurrentLocale().get().getZoneID()).format(MainFormController.getCurrentLocale().get().getDateTimeFormatter())));
        creationDateColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getCreatedAt().toString()));
        TableColumn<Flat, Long> areaColumn = new FixedNameTableColumn<>(ColumnNames.AREA_COLUMN);
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        TableColumn<Flat, Long> numberOfRoomsColumn = new FixedNameTableColumn<>(ColumnNames.NUMBER_OF_ROOMS);
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        TableColumn<Flat, String> furnishColumn = new FixedNameTableColumn<>(ColumnNames.FURNISH_COLUMN);
        furnishColumn.setCellValueFactory(new PropertyValueFactory<>("furnish"));
        TableColumn<Flat, View> viewColumn = new FixedNameTableColumn<>(ColumnNames.VIEW_COLUMN);
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        TableColumn<Flat, Transport> transportColumn = new FixedNameTableColumn<>(ColumnNames.TRANSPORT_COLUMN);
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));
        TableColumn<Flat, String> houseNameColumn = new FixedNameTableColumn<>(ColumnNames.HOUSE_NAME_COLUMN);
        houseNameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getHouse().getName()));
        TableColumn<Flat, Long> houseYearColumn = new FixedNameTableColumn<>(ColumnNames.HOUSE_NAME_COLUMN);
        houseYearColumn.setCellValueFactory(cellValue -> new SimpleLongProperty(cellValue.getValue().getHouse().getYear()).asObject());
        TableColumn<Flat, Integer> houseNumberOfFlatsColumn = new FixedNameTableColumn<>(ColumnNames.HOUSE_NUMBER_OF_FLATS_COLUMN);
        houseNumberOfFlatsColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getHouse().getNumberOfFlatsOnFloor()).asObject());

//        TableColumn<Flat, Integer> ownerIdColumn = new FixedNameTableColumn<>(ColumnNames.OWNER_ID_COLUMN);
//        ownerIdColumn.setCellValueFactory(cellValue -> new SimpleIntegerProperty(cellValue.getValue().getOwner().getId()).asObject());

        tableView.getColumns().setAll(idColumn, nameColumn, coordinateXColumn, coordinateYColumn, creationDateColumn,
                areaColumn, numberOfRoomsColumn, furnishColumn, viewColumn, transportColumn, houseNameColumn, houseYearColumn, houseNumberOfFlatsColumn);
    }

}
