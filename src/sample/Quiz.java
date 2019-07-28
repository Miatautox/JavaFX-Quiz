package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import sun.font.FontFamily;


import java.io.*;
import java.util.Observable;
import java.util.Scanner;

public class Quiz extends Application {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        /*
            READ IN THE FILE question.txt INTO THE STRING ARRAY questions
         */
        String[] questions = new String[10];
        String[] userAnswers = new String[10];

        BufferedReader reader;
        try {
            reader = new BufferedReader( new FileReader(new File("question.txt")));

            String line = reader.readLine();
            for(int i=0; line!=null; line = reader.readLine()){
                questions[i] = line;
                i++;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
            FOR DEBBUGIN ONLY
            USED TO VERIFY IF THE LINES FROM question.txr WHERE IMPORTED CORRECTLY.
         */
//        for (int i=0; i< questions.length; i++){
//            System.out.println(questions[i]);
//        }



        /*
            CREATE THE ARRAYS OF ALL THE REPEATING ELEMENT FROM THE QUESTIONS SECTION
         */
        Label[] qLabel = new Label[10];
        VBox[] questionBox = new VBox[10];
        RadioButton[] tb = new RadioButton[10];
        RadioButton[] fb = new RadioButton[10];
        ToggleGroup[] group = new ToggleGroup[10];
        HBox[] fAnswer = new HBox[10];

        /*
            REGION USED TO CENTER THE HBOX
         */
        Region[] leftSpace = new Region[2];
        Region[] rightSpace = new Region[2];


        Text heading = new Text("Science Trivia Quiz");
        heading.setFont(Font.font(Font.getDefault().getName(),FontWeight.BOLD,20));
        heading.setY(10);
        leftSpace[0] = new Region();
        rightSpace[0] = new Region();
        HBox.setHgrow(leftSpace[0],Priority.ALWAYS);
        HBox.setHgrow(rightSpace[0],Priority.ALWAYS);
        HBox headingBox = new HBox(leftSpace[0],heading,rightSpace[0]);


        /*
            INSTATIATES ALL OF THE ELEMENTS NEEDED FOR THE 10 QUESTIONS
         */
        for (int i=0 ;i < questions.length; i++){
            qLabel[i]=new Label(i+1+". "+questions[i]);//+question[0]);//question.getQuestion(0));//);
            group[i] = new ToggleGroup();
            tb[i] = new RadioButton("True");
            fb[i] = new RadioButton("False");
            tb[i].setPadding(new Insets(3)); // sets spacing in pixels around the True buttons.
            fb[i].setPadding(new Insets(2,2,8,2)); // sets spacing in pixels around the False buttons.
            tb[i].setToggleGroup(group[i]);
            fb[i].setToggleGroup(group[i]);
//            leftSpace[i] = new Region();
//            rightSpace[i] = new Region();

//            vQuestionBox[i] = new VBox(qLabel[i],tb[i],fb[i]);
//            questionBox[i] = new HBox(leftSpace[i],vQuestionBox[i],rightSpace[i]);
            questionBox[i]=new VBox(qLabel[i],tb[i],fb[i]);
            questionBox[i].setAlignment(Pos.CENTER_LEFT);
            questionBox[i].setPadding(new Insets(3,20,3,20));
        }

        /*
            CREATE AND INSTATIATE THE BOX FOR THE SUBMIT BUTTON
         */
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(new Submit(userAnswers));
        submitBtn.setPadding(new Insets(3));
        leftSpace[1] = new Region();
        rightSpace[1] = new Region();
        HBox.setHgrow(leftSpace[1],Priority.ALWAYS);
        HBox.setHgrow(rightSpace[1], Priority.ALWAYS);
        HBox submitBox = new HBox(leftSpace[1],submitBtn,rightSpace[1]);

        /*
            SET TOGGLE LISTENERS IN ORDER TO ADD THE ANSWER OF EACH QUESTION TO THE ARRAY OF userAnswers
        */
        for ( int i=0; i < group.length;i++) {
            group[i].selectedToggleProperty().addListener(new ToggleListener(i,group,userAnswers));
        }

        //for true answers
//        Label t=new Label("True ");
//        Button tb=new Button();
//        //button click event
//        tb.setOnAction(new trueResponse());
//        HBox tAnswer=new HBox(tb,t);
//
//        //for false answer
//        Label f=new Label("False ");
//        Button fb=new Button();
//        //button click event
//        fb.setOnAction(new falseResponse());
//        HBox fAnswer=new HBox(fb,f);

//        q[1]=new Label(questions[1]);//+question[0]);//question.getQuestion(0));//);
//        questionBox[1]=new HBox(q[1]);

//        //for true answers
//        Label t2=new Label("True ");
//        Button tb2=new Button();
//        //button click event
//        tb2.setOnAction(new trueResponse());
//        HBox tAnswer2=new HBox(tb2,t2);
//
//        //for false answer
//        Label f2=new Label("False ");
//        Button fb2=new Button();
//        //button click event
//        fb2.setOnAction(new falseResponse());
//        HBox fAnswer2=new HBox(fb2,f2);


        // PARENT NODE
        GridPane gridpane=new GridPane();
        GridPane.setRowIndex(headingBox,0);

        /*
            SETS ALL THE VBox FOR THE QUESTIONS IN THE GRIDPANE
            i is used as the main index,
            r is used to increment the row of each question box by 3
            t is used to increment the row value of all the True buttons by 3
            f is used to increment the row value of all the False buttons by 3
        */
        for (int i=0; i < questionBox.length ; i++){
            GridPane.setRowIndex(questionBox[i],i+1);
        }

        /*
            SETS THE HBox FOR THE SUBMIT BUTTON IN THE GRIDPANE.
         */
        GridPane.setConstraints(submitBox,0,11);

        /*
            Adding all Children to Parent Node
         */
        gridpane.getChildren().addAll(
                headingBox,
                questionBox[0],
                questionBox[1],
                questionBox[2],
                questionBox[3],
                questionBox[4],
                questionBox[5],
                questionBox[6],
                questionBox[7],
                questionBox[8],
                questionBox[9],
                submitBox
        );

        /*
            CREATED THE SCROLLPANE IN CASE THE PANE IS RESIZED
         */
        ScrollPane scPane = new ScrollPane(gridpane);// added a Scroll pane in order to allow scrolling.
        Scene scene=new Scene(scPane,625,900);
        scene.setFill(Color.LIGHTGRAY);


        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(500);

        primaryStage.setTitle("Group Project Quiz");
        primaryStage.show();



    }

    public class ToggleListener implements ChangeListener<Toggle> {
        private int i;
        private ToggleGroup[] group;
        private String[] userAnswers;
        public ToggleListener(int i,ToggleGroup[] group, String[] userAnswers){
            this.i =i;
            this.group = group;
            this.userAnswers = userAnswers;
        }

        @Override
        public void changed(ObservableValue<? extends Toggle> observable,Toggle oldValue, Toggle newValue ){
            RadioButton selectBtn = (RadioButton) group[i].getSelectedToggle();
            if (selectBtn != null) {
                userAnswers[i] = selectBtn.getText();
            }
        }
    }

}
