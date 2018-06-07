package fr.univ_amu.iut.exercice8;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Paddle extends Rectangle {
    private DoubleProperty paddleY = new SimpleDoubleProperty();

    private double initPaddleTranslateY;
    private double paddleDragAnchorY ;

    public Paddle(double x) {
        super(x,70,20,50);
        this.setFill(Color.BLUE);

        paddleY.setValue(x);
        EventHandler<MouseEvent> hover = event -> {
            setCursor(Cursor.CLOSED_HAND);
            System.out.println("hover");
        };
        this.setOnMouseEntered(hover);

        EventHandler<MouseEvent> init = event -> {

            initPaddleTranslateY = this.getY();
            paddleDragAnchorY = event.getSceneY();
        };

        this.setOnMousePressed(init);

        EventHandler<MouseEvent> handler = event -> {

            double offsetY = event.getSceneY() - paddleDragAnchorY + paddleY.getValue();
            this.setY(this.getY() + offsetY);
            paddleDragAnchorY = event.getSceneY();

        };

        this.setOnMouseDragged(handler);

        paddleY.bind(translateYProperty());
    }
}
