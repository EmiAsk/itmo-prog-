package se.ifmo.lab08.client.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.util.Duration;
import se.ifmo.lab08.client.App;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.resourcebundles.enums.FlatFormElements;
import se.ifmo.lab08.client.resourcebundles.enums.RuntimeOutputs;
import se.ifmo.lab08.client.resourcebundles.enums.VisualisationFormElements;
import se.ifmo.lab08.client.util.AlertPrinter;
import se.ifmo.lab08.client.util.FlatFieldsValidator;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.client.vizualization.FlatSprite;
import se.ifmo.lab08.common.entity.Coordinates;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VisualizerFormController {
    @FXML
    private Label coordinateXLabel;

    @FXML
    private Scene mainScene;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private TextField coordinateXTextField;

    @FXML
    private TextField coordinateYTextField;

    @FXML
    private Slider heightSlider;

    @FXML
    private Button backToTableButton;

    @FXML
    private Pane canvasPane;

    @FXML
    private Label canvasXLabel;

    @FXML
    private Label canvasYLabel;

    private final float HEIGHT_SLIDER_DEFAULT_VALUE = 1;

    private static Canvas canvas = new Canvas();

    private static Timeline frameTimer;

    private final static double SCALE_COMPUTING_RADIUS_MULTIPLIER = 0.67;

    private static final Affine defaultTransform = canvas.getGraphicsContext2D().getTransform();

    private List<Flat> collection;

    private final int CIRCLE_POLYGON_POINTS_COUNT = 100;


    private static final int CANVAS_DEFAULT_WIDTH = 580;

    private static final int CANVAS_DEFAULT_HEIGHT = 580;

    private static final int DEFAULT_STROKE_WIDTH = 5;

    private final float DEFAULT_RADIUS_VALUE = 100;

    private final double FRAMES_PER_SECOND = 31;

    private final int SECOND = 1000;

    private static volatile ArrayList<FlatSprite> flatSprites;

    private Flat selectedFlat;

    private static final Client client = Client.getInstance();

    private IOProvider provider = new IOProvider(new Scanner(System.in), new NotificationPrinter());

    private CommandManager commandManager = new CommandManager(Client.getInstance(), provider, 0);

    private static final Printer errorPrinter = new AlertPrinter();


    static {
        configureCanvas();
    }

    @FXML
    public void initialize() {
        coordinateXTextField.setPromptText(FlatFormElements.COORDINATE_X_TEXT_FIELD.toString());
        coordinateYTextField.setPromptText(FlatFormElements.COORDINATE_Y_TEXT_FIELD.toString());
        coordinateXTextField.textProperty().addListener(change -> FlatFieldsValidator.coordinateXValidate(coordinateXTextField));
        coordinateYTextField.textProperty().addListener(change -> FlatFieldsValidator.coordinateYValidate(coordinateYTextField));

//        musicBandPolygons = new ArrayList<>();
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
        updateLocale();
        canvas.setOnMouseClicked(this::onCanvasMouseClicked);
        canvas.setOnMouseMoved(this::onCanvasMouseMoved);
        canvasPane.getChildren().add(canvas);
        flatSprites = new ArrayList<>();
        for (var flat : new ArrayList<>(MainFormController.getMainFormController().getModelsCollection())) {
            var x = (double) flat.getCoordinates().getX();
            var y = (double) flat.getCoordinates().getY();
            var sprite = new FlatSprite(flat, x, y, canvas);
            flatSprites.add(sprite);
        }
        frameTimer = new Timeline(new KeyFrame(Duration.millis(0)), new KeyFrame(
                Duration.millis(SECOND / FRAMES_PER_SECOND),
                this::drawModels
        ));
        frameTimer.setCycleCount(Timeline.INDEFINITE);
        frameTimer.play();
    }

    private void updateLocale() {
        coordinateXLabel.setText(VisualisationFormElements.COORDINATE_X_LABEL.toString());
        coordinateYLabel.setText(VisualisationFormElements.COORDINATE_Y_LABEL.toString());
        backToTableButton.setText(VisualisationFormElements.BACK_TO_THE_TABLE_BUTTON.toString());
    }

    private static void configureCanvas() {
        canvas.maxHeight(Double.POSITIVE_INFINITY);
        canvas.maxWidth(Double.POSITIVE_INFINITY);
        canvas.setHeight(CANVAS_DEFAULT_HEIGHT);
        canvas.setWidth(CANVAS_DEFAULT_WIDTH);
        canvas.setScaleY(1);
        canvas.setScaleX(1);
        canvas.getGraphicsContext2D().setLineWidth(DEFAULT_STROKE_WIDTH);
    }

    private void drawModels(ActionEvent event) {
        canvas.getGraphicsContext2D().setTransform(defaultTransform);
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        computeCanvasScale();
//        initPolygons();
//        musicBandPolygons.sort((o1, o2) -> Float.compare(o1.getRadius(), o2.getRadius()));
        for (FlatSprite sprite : flatSprites) {
            drawModel(sprite);
        }
//        frameTimer.playFromStart();
    }

    private void computeCanvasScale() {
//        Flat[] modelsArray = collection.toArray(Flat[]::new);
//
//        double canvasComputedWidth = Arrays.stream(modelsArray).mapToDouble(x ->
//                x.getCoordinates().getX() + (x.getFrontMan().getHeight() == null ?
//                        DEFAULT_RADIUS_VALUE * SCALE_COMPUTING_RADIUS_MULTIPLIER :
//                        x.getFrontMan().getHeight() * SCALE_COMPUTING_RADIUS_MULTIPLIER)).max().getAsDouble();
//
//        double canvasComputedHeight = Arrays.stream(modelsArray).mapToDouble(x ->
//                x.getCoordinates().getY() + (x.getFrontMan().getHeight() == null ?
//                        DEFAULT_RADIUS_VALUE* SCALE_COMPUTING_RADIUS_MULTIPLIER :
//                        x.getFrontMan().getHeight() * SCALE_COMPUTING_RADIUS_MULTIPLIER)).max().getAsDouble();
//
//        if (Math.max(canvasComputedHeight, canvasComputedWidth) != 0) {
//            canvas.getGraphicsContext2D().scale(canvas.getWidth() / Math.max(canvasComputedHeight, canvasComputedWidth), canvas.getWidth() / Math.max(canvasComputedHeight, canvasComputedWidth));
//        }
//            canvas.getGraphicsContext2D().scale(canvas.getWidth() / 400, canvas.getHeight() / 500);
    }

    private void initPolygons() {
//        CirclesComputer circlesComputer = new CirclesComputer();
//        musicBandPolygons.clear();
//        for (Flat musicBand : collection) {
//            float radius = musicBand.getFrontMan().getHeight() == null ? DEFAULT_RADIUS_VALUE : musicBand.getFrontMan().getHeight();
//            Point2D center = new Point2D(musicBand.getCoordinates().getX(), musicBand.getCoordinates().getY());
//            Point2D[] points = circlesComputer.calculateCirclePolygon(center, radius, CIRCLE_POLYGON_POINTS_COUNT);
//            FlatPolygon musicBandPolygon = new FlatPolygon(musicBand, center, radius);
//            musicBandPolygon.updatePoints(points);
//            musicBandPolygons.add(musicBandPolygon);
//        }
    }

    private void drawModel(FlatSprite sprite) {
//        canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        sprite.draw();
        sprite.refresh();

        var username = sprite.getFlat().getOwner().getUsername();
        canvas.getGraphicsContext2D().setStroke(username.equals(client.credentials().getUsername()) ? Color.GREEN : Color.RED);
        canvas.getGraphicsContext2D().strokeRect(sprite.getX(), sprite.getY(), FlatSprite.getWidth(), FlatSprite.getHeight());
//        PolygonsFacade polygonsFacade = PolygonsFacade.getInstance();
//        Affine affine = canvas.getGraphicsContext2D().getTransform();
//        for (FlatPolygon polygonToCompare : musicBandPolygons) {
//            if (polygonToCompare == polygon) continue;
//            polygonsFacade.checkPolygons(polygon, polygonToCompare);
//            polygonsFacade.checkBorder(polygon, new StraightLineEquation(0, 1, 0));
//            polygonsFacade.checkBorder(polygon, new StraightLineEquation(0, 1, -canvas.getHeight() / affine.getMyy()));
//            polygonsFacade.checkBorder(polygon, new StraightLineEquation(1, 0, 0));
//            polygonsFacade.checkBorder(polygon, new StraightLineEquation(1, 0, -canvas.getWidth() / affine.getMxx()));
//
//        }
//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//        configureGC(polygon.getFlat().getOwnerId());
//        Point2D[] points = polygon.getPointsFromPolygon();
//        graphicsContext.fillPolygon(Arrays.stream(points).mapToDouble(Point2D::getX).toArray(), Arrays.stream(points).mapToDouble(Point2D::getY).toArray(), points.length);
//        graphicsContext.strokePolygon(Arrays.stream(points).mapToDouble(Point2D::getX).toArray(), Arrays.stream(points).mapToDouble(Point2D::getY).toArray(), points.length);
    }

    private void clearResources() {
        frameTimer.stop();
        flatSprites = null;
        canvasPane.getChildren().clear();
    }

    private void configureGC(int ownerId) {
//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//        if (ownerId == ClientInfo.getUserId()) {
//            graphicsContext.setStroke(Color.GREEN);
//            graphicsContext.setFill(Color.LIGHTGREEN);
//        } else {
//            graphicsContext.setStroke(Color.DARKGREY);
//            graphicsContext.setFill(Color.LIGHTGREY);
//        }
    }

    @FXML
    protected void onCanvasMouseClicked(MouseEvent event) {
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        double newX = event.getX() / affine.getMxx();
        double newY = event.getY() / affine.getMyy();
        FlatSprite flatSprite = null;
        for (FlatSprite sprite : flatSprites) {
            if (sprite.contains(newX, newY)) {
                var currentUsername = client.credentials().getUsername();
                if (!sprite.getFlat().getOwner().getUsername().equals(currentUsername)) {
                    provider.getPrinter().print(RuntimeOutputs.MANAGE_FLATS.toString());
                    return;
                }
                flatSprite = sprite;
            }
        }
        if (flatSprite == null) {
            resetSelection();
            return;
        }
        selectPolygon(flatSprite);
    }

    @FXML
    protected void onBackToTableButtonPressed(ActionEvent actionEvent) {
        App.getPrimaryStage().setResizable(true);
        App.getPrimaryStage().setScene(MainFormController.getMainFormController().getPrimaryScene());
        clearResources();
    }

    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent) {
        if (selectedFlat == null) {
            provider.getPrinter().print(RuntimeOutputs.FLAT_WAS_NOT_SELECTED_IN_TABLE.toString());
            return;
        }
        if (!(FlatFieldsValidator.coordinateXValidate(coordinateXTextField) && FlatFieldsValidator.coordinateYValidate(coordinateYTextField))) {
            provider.getPrinter().print(RuntimeOutputs.FIELDS_NOT_VALID.toString());
            return;
        }

        Coordinates coordinates = new Coordinates(Integer.parseInt(coordinateXTextField.getText()), Float.parseFloat(coordinateYTextField.getText()));
        selectedFlat.setCoordinates(coordinates);
        Platform.runLater(() -> {
            try {
                commandManager.executeServerCommand("update", new String[]{String.valueOf(selectedFlat.getId())}, selectedFlat);
                resetSelection();
            } catch (IOException | ClassNotFoundException e) {
                errorPrinter.print(RuntimeOutputs.SOMETHING_WENT_WRONG.toString());
                throw new RuntimeException(e);
            } catch (TimeLimitExceededException ignored) {
                errorPrinter.print(RuntimeOutputs.SERVER_DOES_NOT_RESPOND.toString());
            }
        });
    }

    private void resetSelection() {
        coordinateXTextField.setText("");
        coordinateYTextField.setText("");
        selectedFlat = null;
    }

    private void selectPolygon(FlatSprite sprite) {
        coordinateXTextField.setText(String.valueOf(sprite.getFlat().getCoordinates().getX()));
        coordinateYTextField.setText(String.valueOf(sprite.getFlat().getCoordinates().getY()));
//        Float height = sprite.getFlat().getFrontMan().getHeight();
//        heightSlider.setValue(height == null ? 100 : height);
        selectedFlat = sprite.getFlat();
    }

    public static void removeSprite(long id) {
        if (flatSprites == null) {
            return;
        }
        flatSprites.removeIf(sprite -> sprite.getFlat().getId() == id);
    }

    public static void addSprite(Flat flat) {
        if (flatSprites == null) {
            return;
        }
        var x = (double) flat.getCoordinates().getX();
        var y = (double) flat.getCoordinates().getY();
        var sprite = new FlatSprite(flat, x, y, canvas);
        flatSprites.add(sprite);
    }

    public static void updateSprite(Flat flat) {
        if (flatSprites == null) {
            return;
        }
        var x = (double) flat.getCoordinates().getX();
        var y = (double) flat.getCoordinates().getY();
        for (var sprite : flatSprites) {
            if (sprite.getFlat().getId() == flat.getId()) {
                sprite.setTarget(x, y);
            }
        }
    }

    public static ArrayList<FlatSprite> getFlatSprites() {
        return flatSprites;
    }

    @FXML
    protected void onButtonMouseEntered(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 2;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    @FXML
    protected void onButtonMouseExited(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 1;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    private void onCanvasMouseMoved(MouseEvent mouseEvent) {
        Affine affine = canvas.getGraphicsContext2D().getTransform();
        Integer newX = (int) Math.round(mouseEvent.getX() / affine.getMxx());
        Integer newY = (int) Math.round(mouseEvent.getY() / affine.getMyy());
        canvasXLabel.setText(newX.toString());
        canvasYLabel.setText(newY.toString());
    }
}
