package com.example.introduccio_jocs;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class HelloApplication extends Application {
    IntValue puntos = new IntValue(0);
    IntValue vidas = new IntValue(5);
    ArrayList<Sprite> crojoList = new ArrayList<Sprite>();
    Sprite crojo;
    ArrayList<Sprite> enemigosList = new ArrayList<Sprite>();
    Sprite enemigo;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage theStage) throws InterruptedException {
        theStage.setTitle("Pol-Pac-Man");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1000, 600);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!input.contains(code))
                    input.add(code);
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                input.remove(code);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
            Sprite comecocos = new Sprite();
            comecocos.setImage("file:comecocos.png");
            comecocos.setPosition(450, 400);

        LongValue lastNanoTime = new LongValue(System.nanoTime());


            new AnimationTimer() {

                public void handle(long currentNanoTime){
                    if (vidas.value > 0) {
                        // calculate time since last update.
                        double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                        lastNanoTime.value = currentNanoTime;

                        if (puntos.value < 5) {
                            if (currentNanoTime % 8000 == 0) {
                                crojo = new Sprite();
                                crojo.setImage("file:comida1.png");
                                double px = 900 * Math.random() + 50;
                                double py = 0;
                                crojo.setPosition(px, py);
                                crojoList.add(crojo);
                            }
                        } else if (puntos.value >= 5 && puntos.value < 20){
                            if (currentNanoTime % 3000 == 0) {
                                crojo = new Sprite();
                                crojo.setImage("file:comida1.png");
                                double px = 900 * Math.random() + 50;
                                double py = 0;
                                crojo.setPosition(px, py);
                                crojoList.add(crojo);
                            }

                            if (currentNanoTime % 8000 == 0) {
                                enemigo = new Sprite();
                                enemigo.setImage("file:enemigo.png");
                                double px = 900 * Math.random() + 50;
                                double py = 0;
                                enemigo.setPosition(px, py);

                                enemigosList.add(enemigo);
                            }
                        } else if (puntos.value >= 20 && puntos.value < 30) {
                            if (currentNanoTime % 2000 == 0) {
                                crojo = new Sprite();
                                crojo.setImage("file:comida1.png");
                                double px = 900 * Math.random() + 50;
                                double py = 0;
                                crojo.setPosition(px, py);
                                crojoList.add(crojo);
                            }

                            if (currentNanoTime % 5000 == 0) {
                                enemigo = new Sprite();
                                enemigo.setImage("file:enemigo.png");
                                double px = 900 * Math.random() + 50;
                                double py = 0;
                                enemigo.setPosition(px, py);

                                enemigosList.add(enemigo);
                            }
                        }
/*

*/
                        for (Sprite sprite : crojoList) {
                            int vx = 0;
                            int vy = (int) (Math.random() * 50 + 1);
                            sprite.setVelocity(vx, vy);
                            sprite.update(elapsedTime);
                        }
                        for (Sprite sprite : enemigosList) {
                            int vx = 0;
                            int vy = (int) (Math.random() * 50 + 1);
                            sprite.setVelocity(vx, vy);
                            sprite.update(elapsedTime);
                        }


                        // game logic
                        comecocos.setVelocity(0, 0);
                        if (input.contains("LEFT"))
                            comecocos.addVelocity(-200, 0);
                        if (input.contains("RIGHT"))
                            comecocos.addVelocity(200, 0);
                        if (input.contains("UP"))
                            comecocos.addVelocity(0, -200);
                        if (input.contains("DOWN"))
                            comecocos.addVelocity(0, 200);

                        comecocos.update(elapsedTime);

                        // collision detection

                        Iterator<Sprite> crojoIter = crojoList.iterator();
                        while (crojoIter.hasNext()) {
                            Sprite crojo = crojoIter.next();
                            if (comecocos.intersects(crojo)) {
                                crojoIter.remove();
                                puntos.value++;
                            }
                        }

                        Iterator<Sprite> enemIter = enemigosList.iterator();
                        while (enemIter.hasNext()) {
                            Sprite enemigo = enemIter.next();
                            if (comecocos.intersects(enemigo)) {
                                enemIter.remove();
                                vidas.value--;
                            }
                        }

                        // render
                        gc.clearRect(0, 0, 1000, 600);
                        comecocos.render(gc);

                        for (Sprite crojo : crojoList)
                            crojo.render(gc);

                        for (Sprite enemigo : enemigosList)
                            enemigo.render(gc);

                        String pointsText = "Punts: " + (puntos.value);
                        gc.fillText(pointsText, 850, 36);
                        gc.strokeText(pointsText, 850, 36);

                        String pointsTextvidas = "Vides: " + vidas.value;
                        gc.fillText(pointsTextvidas, 30, 36);
                        gc.strokeText(pointsTextvidas, 30, 36);
                    } else {
                        String gameover = "GAME OVER";
                        gc.setFill( Color.RED );
                        gc.setStroke( Color.BLACK );
                        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 75 );
                        gc.setFont( theFont );
                        gc.fillText(gameover, 280, 300);
                        gc.strokeText(gameover, 280, 300);


                    }
                }

            }.start();


        theStage.show();
    }
}