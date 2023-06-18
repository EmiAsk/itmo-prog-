package se.ifmo.lab08.client.tableHandlers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import se.ifmo.lab08.common.entity.Flat;

import java.util.List;
import java.util.function.Predicate;

public class TableViewHandler {
    private TableView<Flat> tableView;

    private ObservableList<Flat> modelsCollection;

    private FilteredList<Flat> filteredList;

    private SortedList<Flat> sortedList;

    private static ObservableList<Predicate<Flat>> predicates;

    public TableViewHandler(TableView<Flat> tableView, ObservableList<Flat> modelsCollection) {
        this.tableView = tableView;
        this.modelsCollection = modelsCollection;
        predicates = FXCollections.observableArrayList();
        predicates.addListener(this::listChanged);
        filteredList = new FilteredList<>(modelsCollection);
        filteredList.setPredicate(this::checkPredicates);
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    public void initializeColumns() {
        TableColumnsInitializer tableColumnsInitializer = new TableColumnsInitializer(tableView);
        tableColumnsInitializer.initializeColumns();
        listChanged(null);
    }

    private void listChanged(ListChangeListener.Change change) {
        filteredList.setPredicate(this::checkPredicates);
    }

    private boolean checkPredicates(Object o) {
        for (Predicate predicate : predicates) {
            if (!predicate.test(o)) {
                return false;
            }
        }
        return true;
    }

    public void addElement(Flat musicBand) {
        Platform.runLater(() -> modelsCollection.add(musicBand));
    }

    public void updateElement(Flat newFlat) {
        if (modelsCollection.removeIf(oldFlat -> oldFlat.getId() == newFlat.getId())) {
            modelsCollection.add(newFlat);
        }
    }

    public void removeElement(List<Long> ids) {
        Platform.runLater(() -> modelsCollection.removeIf(band -> ids.contains(band.getId())));
    }

    public static List<Predicate<Flat>> getPredicates() {
        return predicates;
    }

    public SortedList<Flat> getSortedList() {
        return sortedList;
    }

}
