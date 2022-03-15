package com.example.introduccio_jocs;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);

    @Override
    public void start(Stage stage) throws Exception {
        Circle circle = createCircle();
        Group group = new Group(createVidas(), createPuntos(), circle);
        TranslateTransition transition = createTranslateTransition(circle);

        Scene scene = new Scene(group, 1000, 600, Color.CORNSILK);
        moveCircleOnMousePress(scene, circle, transition);

        stage.setScene(scene);
        stage.show();
    }

    private Label createVidas() {
        Label vidas = new Label("Vidas: ");
        vidas.setTextFill(Color.FORESTGREEN);
        return vidas;
    }
    private Label createPuntos() {
        Label vidas = new Label("Puntos: ");
        vidas.setTextFill(Color.FORESTGREEN);
        return vidas;
    }

    private Circle createCircle() {
        final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
        circle.setOpacity(0.7);
        return circle;
    }

    private TranslateTransition createTranslateTransition(final Circle circle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
                circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
                circle.setTranslateX(0);
                circle.setTranslateY(0);
            }
        });
        return transition;
    }

    private void moveCircleOnMousePress(Scene scene, final Circle circle, final TranslateTransition transition) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                transition.setToX(event.getSceneX() - circle.getCenterX());
                transition.setToY(event.getSceneY() - circle.getCenterY());
                transition.playFromStart();
            }
        });
    }

    public static void main(String[] args) { launch(args); }
}