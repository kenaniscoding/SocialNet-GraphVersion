package com.example.lab7v2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Controller {

    public TextField nameField;
    public Label statusLabel;
    public TextField statusChangeLabel;
    public TextField pictureLabel;
    public TextField friendLabel;
    public TextField unfriendLabel;
    public TextField quoteLabel;
    public ImageView pictureView;
    public ListView listView;
    //ADDED
    public Label name;
    public Label quote;
    public Label status;
    public ProfileData profileData;
    private static ArrayList<String> friendNames = new ArrayList<>();
    private static HashMap<String, ArrayList<ProfileData>> list = new HashMap<>();
    ObservableList<String> friendList;

    @FXML
    private void initialize(){
        friendList = FXCollections.observableArrayList();
        listView.setItems(friendList);
    }
    public void handleAdd(ActionEvent actionEvent) {
        if (!nameField.getText().trim().isEmpty()) {
            if (!list.containsKey(nameField.getText())){
                statusLabel.setText(nameField.getText() + " added");
                //ADD PROFILE/NAME
                profileData = new ProfileData();
                profileData.setName(nameField.getText());
                //label name
                name.setText(nameField.getText());
                addVertex(nameField.getText());
                list.get(nameField.getText()).add(profileData);
                friendNames.add(nameField.getText());
                System.out.println("\nNEW PROFILE "+nameField.getText());
//                namesList.add(nameField.getText());

                friendList.clear();
                statusChangeLabel.setText("");
                pictureLabel.setText("");
                friendLabel.setText("");
                unfriendLabel.setText("");
                quoteLabel.setText("");
                profileData.setPicture("C:\\Users\\Kenan\\Documents\\DLSU Files\\4th Term\\LBYCPA2\\Lab7v2\\src\\main\\java\\com\\example\\lab7v2\\unknown.png");
                pictureView.setImage(profileData.getImage());
                status.setText("");
                quote.setText("");
                //REMOVES TEXT
                nameField.setText("");
            } else {
                System.out.println("profile already exists");
                statusLabel.setText(nameField.getText()+" already exists as a profile");
                int index = findIndex(nameField.getText());
                profileData = list.get(nameField.getText()).get(index);
                changeProfileDisplay(index);
            }

        }
    }

    private void changeProfileDisplay(int index) {
        name.setText(list.get(nameField.getText()).get(index).getName());
        quote.setText(list.get(nameField.getText()).get(index).getQuote());
        status.setText(list.get(nameField.getText()).get(index).getStatus());
        pictureView.setImage(list.get(nameField.getText()).get(index).getImage());
        friendList.clear();
        friendList.addAll(list.get(nameField.getText()).get(index).getFriend());
    }

    public void addVertex(String vertex) {
        if (!list.containsKey(vertex)) {
            list.put(vertex, new ArrayList<>());
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        if (!nameField.getText().trim().isEmpty()) {
            friendNames.remove(nameField.getText());
            statusLabel.setText(nameField.getText() + " deleted");
//            profileNameList.remove(nameField.getText());
            list.remove(nameField.getText());
            System.out.println("REMOVED PROFILE "+nameField.getText());
            for (String friendName : friendNames) {
                int index = findIndex(friendName);
                ArrayList<String> friends = list.get(friendName).get(index).getFriend();
                friends.remove(nameField.getText());
            }
            //REMOVES TEXT
            nameField.setText("");
        }
    }

    public void handleLookup(ActionEvent actionEvent) {
        if (!nameField.getText().trim().isEmpty()) {
            statusLabel.setText(nameField.getText() + " lookup");
            //SEARCH FOR PROFILE/NAME
            if (list.containsKey(nameField.getText())){
                statusLabel.setText("profile "+nameField.getText()+" loaded");
                int index = findIndex(nameField.getText());
                profileData = list.get(nameField.getText()).get(index);
                changeProfileDisplay(index);
//                System.out.println(list.get(nameField.getText()).get(0).getName());
            } else {
                System.out.println("profile "+nameField.getText()+" does not exist");
                statusLabel.setText("profile "+nameField.getText()+" does not exist");
            }
            nameField.clear();
        }
    }
    private int findIndex(String name){
        for (int i=0; i<list.size();i++){
            if (list.containsKey(name)){
//                System.out.println("index "+i+" is named "+list.get(nameField.getText()).get(i).getName());
                return i;
            }
        }
        return -1;
    }
    public void handleStatus(ActionEvent actionEvent) {
        if (!statusChangeLabel.getText().trim().isEmpty()) {
            statusLabel.setText("status changed to " + statusChangeLabel.getText());
            System.out.println("STATUS: "+statusChangeLabel.getText());
            //CHANGE STATUS works
            profileData.setStatus(statusChangeLabel.getText());
            int index = findIndex(profileData.getName());
            list.get(profileData.getName()).get(index).setStatus(statusChangeLabel.getText());

            status.setText(statusChangeLabel.getText());
            //REMOVE TEXT
            statusChangeLabel.setText("");
        }
    }

    public void handlePicture(ActionEvent actionEvent) {
        if (!pictureLabel.getText().trim().isEmpty()) {
            //change this?
            pictureView.setImage(new Image(pictureLabel.getText()));
            statusLabel.setText("picture changed to " + pictureLabel.getText());
            //CHANGE PICTURE works
//            profileData.setPicture(pictureLabel.getText());
            System.out.println(profileData.getName()+" is the profile name");
            int index = findIndex(profileData.getName());
            list.get(profileData.getName()).get(index).setPicture(pictureLabel.getText());
            //REMOVE TEXT
            pictureLabel.setText("");
        }
    }
    public void handleAddFriend(ActionEvent actionEvent) {
        if (!friendLabel.getText().trim().isEmpty()) {
            // if friend exists in list.get(profile.getName()) then connect them
            if (list.containsKey(friendLabel.getText())){
                friendList.add(friendLabel.getText());
                profileData.addFriend(friendLabel.getText());
                int index = findIndex(friendLabel.getText());
                list.get(friendLabel.getText()).get(index).addFriend(profileData.getName());
                System.out.println(friendLabel.getText() + " is friend");
                statusLabel.setText(friendLabel.getText() + " added as friend");
            } else { // if friend doesn't exists then make a new profile for them
                friendNames.add(friendLabel.getText());
                System.out.println("friend "+friendLabel.getText()+" is a new profile");
                statusLabel.setText("friend "+friendLabel.getText()+" added a new profile and friend list");
                ProfileData temp = new ProfileData();
                temp.setName(friendLabel.getText());
                temp.addFriend(profileData.getName());
                temp.setPicture("C:\\Users\\Kenan\\Documents\\DLSU Files\\4th Term\\LBYCPA2\\Lab7v2\\src\\main\\java\\com\\example\\lab7v2\\unknown.png");
                profileData.addFriend(temp.getName());
                addVertex(temp.getName());
                list.get(temp.getName()).add(temp);
                friendList.add(temp.getName());
//                namesList.add(temp.getName());
            }
            //REMOVE TEXT
            friendLabel.setText("");
        }
    }
    public void handleUnfriend(ActionEvent actionEvent) {
        if (!unfriendLabel.getText().trim().isEmpty()) {
            friendList.remove(unfriendLabel.getText());
            System.out.println("UNFRIEND: "+unfriendLabel.getText());
            profileData.removeFriend(unfriendLabel.getText());
            int index=profileData.getName().indexOf(profileData.getName());
            list.get(profileData.getName()).get(index).removeFriend(unfriendLabel.getText());
            int index2 = findIndex(unfriendLabel.getText());
            list.get(unfriendLabel.getText()).get(index2).removeFriend(profileData.getName());

            System.out.println(unfriendLabel.getText()+" is NOT friend");
            statusLabel.setText(unfriendLabel.getText() + " REMOVED as friend");
            //REMOVE TEXT
            unfriendLabel.setText("");
        }
    }
    public void handleQuote(ActionEvent actionEvent) {
        if (!quoteLabel.getText().trim().isEmpty()) {
            statusLabel.setText("Quote changed to " + quoteLabel.getText());
            System.out.println("QUOTE: "+quoteLabel.getText());

            int index = findIndex(profileData.getName());
            list.get(profileData.getName()).get(index).setQuote(quoteLabel.getText());

            quote.setText(quoteLabel.getText());
            quoteLabel.setText("");
        }
    }
    @FXML
    private void displayGraph(ActionEvent actionEvent) throws IOException {
        /*
         * TODO if there is time >:)
         * replace friendNames with only list
         */

        Stage stage = new Stage();
        Pane pane = new Pane();
//        System.out.println(Arrays.toString(friendNames.toArray()));
        for (int i=0; i<friendNames.size(); i++){
//            System.out.println("NODE "+(i+1)+" -> "+friendNames.get(i));
//            ArrayList<ProfileData> profile = list.get(friendNames.get(i));
            pane.getChildren().add(i,createCircle(randomNumber(),randomNumber()));
        }
        for (int i=0; i<friendNames.size(); i++){
            Circle vertex = (Circle) pane.getChildren().get(i);
            pane.getChildren().add(friendNames.size()+i,createLabel(friendNames.get(i), vertex.getCenterX(), vertex.getCenterY() - 30));
        }
        for (int i=0; i<friendNames.size(); i++){
            int index = findIndex(friendNames.get(i));
            ArrayList<String> friends = list.get(friendNames.get(i)).get(index).getFriend();
//            System.out.println("friendNames: "+friendNames.get(i)+" -> "+Arrays.toString(friends.toArray()));
//            System.out.println("size="+friends.size());
            for (int j=0; j<friends.size(); j++){
//                    System.out.println(j);
                    Circle m2 = (Circle) pane.getChildren().get(i);
//                    System.out.println("Circle1 -> "+friendNames.get(i)+" index -> "+i);
                    int newIndex = findFriendIndex(friends.get(j));
                    if (newIndex==-1){
//                        System.out.println("-1");
                        break;
                    } else {
                        Circle m1 = (Circle) pane.getChildren().get(newIndex);
//                    System.out.println("Circle2 -> "+friends.get(j)+" index -> "+newIndex);
//                    int index2 = friendNames.size()*2-1;
                        pane.getChildren().add(createLine(m1.getCenterX(), m1.getCenterY(), m2.getCenterX(), m2.getCenterY()));
                    }
            }
        }
        Scene scene = new Scene(pane, 700, 500);
        stage.setScene(scene);
        stage.show();
    }
    private int findFriendIndex(String name){
//        System.out.println("friendNames -> "+ Arrays.toString(friendNames.toArray()));
        for (int i=0; i<friendNames.size();i++){
            if (friendNames.get(i).equals(name)){
//                System.out.println("index "+i+" is named "+list.get(nameField.getText()).get(i).getName());
                return i;
            }
        }
        return -1;
    }

    private Circle createCircle(double centerX, double centerY) {
        return new Circle(centerX, centerY, 20, Color.YELLOW);
    }

    // Helper method to create line edges
    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLACK);
        return line;
    }

    // Helper method to create text labels
    private Text createLabel(String text, double x, double y) {
        Text label = new Text(text);
        label.setFont(Font.font("Arial", 16));
        label.setX(x - label.getBoundsInLocal().getWidth() / 2);
        label.setY(y);
        return label;
    }
    private Integer randomNumber(){
        // Create a Random object
        Random random = new Random();

        // Generate a random number between 100 and 700
        return random.nextInt(601 - 150) + 100;
    }
}
