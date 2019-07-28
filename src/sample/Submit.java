package sample;

import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Submit implements EventHandler<ActionEvent> {

    private String[] answers = new String[10];
    private String[] userAnswers;
    private String[] answersResults = new String[10];
    private String msg ="";
    private int totalScore =0;
    private double gradePer=0;
    private boolean full = true;

    public Submit (String[] userAnswers){
        this.userAnswers = userAnswers;
    }

    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub

        BufferedReader reader;

        for(String s:userAnswers){
            if(s == null) {
                full = false;
                break;
            }
        }

        if (full) {
            try {
                reader = new BufferedReader(new FileReader(new File("answers.txt")));

                String line = reader.readLine();
                for (int i = 0; line != null; line = reader.readLine()) {
                    answers[i] = line;
                    i++;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        /*
            FOR DEBBUGIN ONLY
            USED TO VERIFY IF THE LINES FROM answer.txt WHERE IMPORTED CORRECTLY.
         */
//        for (int i=0; i< answers.length; i++){
//            System.out.println("This are the answers from the File answers.txt: "+answers[i]);
//            System.out.println("This are the userAnswers: "+ userAnswers[i]);
//        }

            for (int i = 0; i < answers.length; i++) {
                if (userAnswers[i] == null) {
                    answersResults[i] = "Question " + (i + 1) + ": Was not answered.";
                }
                if (userAnswers[i].equals(answers[i])) {
                    totalScore++;
                    answersResults[i] = "Question " + (i + 1) + ": " + userAnswers[i] + " is Correct.";
                } else
                    answersResults[i] = "Question " + (i + 1) + ": " + userAnswers[i] + " is Incorrect";
            }

            gradePer = totalScore * 100 / 10;

//        msg += "Your Score: "+ String.format("%.2f",gradePer) +"%\n";
            for (String s : answersResults) {
                msg += s + "\n";
            }

            final Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
//        Button closeBtn = new Button("Try Again");
            Region leftSpaceHead = new Region();
            Region rightSpaceHead = new Region();
            Region leftSpace = new Region();
            Region rightSpace = new Region();
            Region leftSpaceBtn = new Region();
            Region rightSpaceBtn = new Region();
            HBox.setHgrow(leftSpaceHead, Priority.ALWAYS);
            HBox.setHgrow(rightSpaceHead, Priority.ALWAYS);
            HBox.setHgrow(leftSpace, Priority.ALWAYS);
            HBox.setHgrow(rightSpace, Priority.ALWAYS);
            HBox.setHgrow(leftSpaceBtn, Priority.ALWAYS);
            HBox.setHgrow(rightSpaceBtn, Priority.ALWAYS);
            HBox headerBox = new HBox(15);
            HBox contentBox = new HBox(5);
            HBox buttonBox = new HBox(5);
            VBox finalBox = new VBox(3);
            Button okBtn = new Button("OK");
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    finalBox.getChildren().removeAll(headerBox, contentBox, buttonBox);
                    for (int i = 0; i < answers.length; i++) {
                        answersResults[i] = "";
                    }
                    totalScore = 0;
                    msg = "";
                    popUpStage.close();
                }
            });
            buttonBox.getChildren().addAll(leftSpaceBtn, okBtn, rightSpaceBtn);
            headerBox.getChildren().addAll(leftSpaceHead, new Text("Your Score: " + String.format("%.2f", gradePer) + "%\n"), rightSpaceHead);
            contentBox.getChildren().addAll(leftSpace, new Text(msg), rightSpace);

            finalBox.getChildren().addAll(headerBox, contentBox, buttonBox);
            Scene popUpScene = new Scene(finalBox, 225, 280);
            popUpScene.setFill(Color.LIGHTGRAY);
            popUpStage.setScene(popUpScene);
            popUpStage.show();

        }else{
            System.out.println("error message");
            final Stage error =new Stage();
            error.initModality(Modality.APPLICATION_MODAL);
            Button okBtn = new Button("OK");
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    error.close();
                }
            });
            Region leftSpace =new Region();
            Region rightSpace =new Region();
            Region leftSpaceBtn =new Region();
            Region rightSpaceBtn =new Region();
            HBox.setHgrow(leftSpace,Priority.ALWAYS);
            HBox.setHgrow(rightSpace,Priority.ALWAYS);
            HBox.setHgrow(leftSpaceBtn,Priority.ALWAYS);
            HBox.setHgrow(rightSpaceBtn,Priority.ALWAYS);
            HBox msgBox = new HBox(leftSpace,new Text("Please answer all of the questions."),rightSpace);
            msgBox.setPadding(new Insets(15));
            HBox buttonBox =new HBox(leftSpaceBtn,okBtn,rightSpaceBtn);
            buttonBox.setPadding(new Insets(10));
            VBox containerBox = new VBox(5);
            containerBox.getChildren().addAll(msgBox,buttonBox);

            Scene errorScene =new Scene(containerBox,250,100, Color.LIGHTGRAY);
            error.setScene(errorScene);
            error.show();
            full = true;


        }
    }
}

