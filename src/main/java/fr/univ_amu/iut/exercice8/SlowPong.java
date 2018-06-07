package fr.univ_amu.iut.exercice8;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import sun.font.TextLabel;

import java.util.stream.DoubleStream;

public class SlowPong extends Application {

    private BooleanProperty startVisible = new SimpleBooleanProperty(true);
    private Ball ball;
    private Pane pongPane;
    private Paddle leftPaddle;
    private Line middleLine = new Line(250,500,250,0);

    private IntegerProperty propScoreP1 = new SimpleIntegerProperty(0);
    private IntegerProperty propScoreP2 = new SimpleIntegerProperty(0);
    private Label scoreP1 = new Label(propScoreP1.getValue().toString());
    private Label scoreP2 = new Label(propScoreP2.getValue().toString());
    private Paddle rightPaddle;
    private Button startButton;
    private AnimationTimer pongAnimation;

    private BooleanExpression isBouncingOffVerticalWall;
    private BooleanExpression isBouncingOffHorizontalWallRight;
    private BooleanExpression isBouncingOffHorizontalWallLeft;

    @Override
    public void start(Stage stage) {
        pongAnimation = createAnimation();

        ball = new Ball();

        leftPaddle = new Paddle(10);
        rightPaddle = new Paddle(475);

        startButton = createStartButton();
        pongPane = createPongPane();

        configureStage(stage);

        createBindings();
        stage.show();
    }

    private void configureStage(Stage stage) {
        Scene scene = new Scene(pongPane);
        stage.setResizable(false);
        stage.setScene(scene);

    }

    private Pane createPongPane() {
        pongPane = new Pane();

        pongPane.setPrefSize(500,500);
        pongPane.setMinSize(500,500);
        pongPane.setMaxSize(500,500);

        pongPane.setStyle("-fx-background-color:black");
        scoreP2.setLayoutX(350);
        scoreP1.setLayoutX(150);
        scoreP1.setLayoutY(10);
        scoreP2.setLayoutY(10);
        startButton.setLayoutX(230);
        startButton.setLayoutY(450);

        middleLine.setStroke(Color.WHITE);
        middleLine.setOpacity(0.5);
        pongPane.getChildren().addAll(middleLine,leftPaddle, rightPaddle ,startButton,ball,scoreP1,scoreP2);

        return pongPane;


    }

    private Button createStartButton() {
        startButton = new Button();
        startButton.setAlignment(Pos.CENTER);
        startButton.setText("Start");
        EventHandler<ActionEvent> click = event -> {
            pongAnimation.start();
            System.out.println("click");

        };
        startButton.setOnAction(click);
        startButton.visibleProperty().bind(startVisible);

        //startVisible.bind(startButton.visibleProperty());
        return startButton;
    }

    private void createBindings() {
        isBouncingOffHorizontalWallRight = ball.centerXProperty().greaterThanOrEqualTo(pongPane.widthProperty().subtract(ball.radiusProperty()));
        isBouncingOffHorizontalWallLeft = ball.centerXProperty().lessThanOrEqualTo(ball.radiusProperty());

        isBouncingOffVerticalWall = ball.centerYProperty().greaterThanOrEqualTo(pongPane.heightProperty().subtract(ball.radiusProperty())).or(
                ball.centerYProperty().lessThanOrEqualTo(ball.radiusProperty()));

        scoreP1.textProperty().bind(propScoreP1.asString());
        scoreP2.textProperty().bind(propScoreP2.asString());
        scoreP2.setTextFill(Color.WHITE);
        scoreP1.setTextFill(Color.WHITE);
    }

    private void updatePong(long elapsedTimeInNanoseconds) {
        checkBouncing();
        moveBall(elapsedTimeInNanoseconds);
    }

    private boolean isBouncingOffPaddles() {
        BooleanExpression bouncing =
                ball.centerXProperty().isEqualTo(ball.radiusProperty().add(leftPaddle.widthProperty())).or(
                        ball.centerXProperty().isEqualTo(pongPane.widthProperty().subtract(rightPaddle.widthProperty()).add(ball.getRadius())
                ));
        return bouncing.getValue();

    }

    private boolean isBouncingOffLeftPaddle() {
        return ball.collided(leftPaddle);
    }

    private boolean isBouncingOffRightPaddle() {
//        BooleanExpression bouncerightpaddle =
//        return bouncerightpaddle.getValue();
        return ball.collided(rightPaddle);


    }

    private boolean isBouncingOffVerticalWall() {

         return isBouncingOffVerticalWall.getValue();
    }

    private boolean isBouncingOffHorizontalWallLeft() {
        return isBouncingOffHorizontalWallLeft.getValue();
    }
    private boolean isBouncingOffHorizontalWallRight() {
        return isBouncingOffHorizontalWallRight.getValue();
    }

    private void checkBouncing() {
        if(isBouncingOffHorizontalWallLeft())
        {
            startNewGame();
            propScoreP2.setValue(propScoreP2.getValue() +  1);
        }
        else if(isBouncingOffHorizontalWallRight())
        {
            startNewGame();
            propScoreP1.setValue(propScoreP1.get() + 1);
        }
        else if(isBouncingOffLeftPaddle())
        {
            ball.setVelocityX(ball.getVelocityX() * -1);
        }
        else if(isBouncingOffRightPaddle())
        {
            ball.setVelocityX(ball.getVelocityX() * -1);
        }
        else if(isBouncingOffVerticalWall())
        {
            ball.setVelocityY(ball.getVelocityY() * - 1);
        }
    }

    private void moveBall(long elapsedTimeInNanoseconds) {
          ball.setCenterX((ball.getCenterX()) + (ball.getVelocityX() * elapsedTimeInNanoseconds));
            ball.setCenterY((ball.getCenterY()) + (ball.getVelocityY() * elapsedTimeInNanoseconds));
    }

    private void startNewGame() {
        pongAnimation.stop();
        ball.setCenterX(250);
        ball.setCenterY(250);


    }

    private AnimationTimer createAnimation() {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);

        return new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    long elapsedTimeInNanoseconds = timestamp - lastUpdateTime.get();
                    updatePong(elapsedTimeInNanoseconds);
                }
                lastUpdateTime.set(timestamp);
            }

            @Override
            public void start() {
                lastUpdateTime.set(System.nanoTime());
                startVisible.set(false);
                super.start();
            }

            @Override
            public void stop() {
                lastUpdateTime.set(System.nanoTime());
                startVisible.set(true);
                super.stop();
            }
        };
    }
}