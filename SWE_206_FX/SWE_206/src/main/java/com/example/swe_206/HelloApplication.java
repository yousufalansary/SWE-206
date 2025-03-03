package com.example.swe_206;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class HelloApplication extends Application {
    static ArrayList<Tournament> tournamentListOG = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        // Load the login screen
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginScene.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 600, 600);

        // Load the scene for creating a new tournament
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CreateNewTournament.fxml"));
<<<<<<< HEAD
        // TournamentCreateScene.scene = new Scene(fxmlLoader.load(), 500, 500);

=======
        CreateNewTournament.scene = new Scene(fxmlLoader.load(), 500, 500);
        
>>>>>>> ee6e703e48a6611a6de495ca7c509a45be889808
        // Set some properties of the stage
        stage.setResizable(false);
        stage.setTitle("login!");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {

        try (FileInputStream inFile = new FileInputStream("currentTournaments.dat");
                ObjectInputStream inObj = new ObjectInputStream(inFile);) {
            Compressor c = (Compressor) inObj.readObject();
            ArrayList<SerializableTournament> st = c.getTournamentList();
            ArrayList<Tournament> tournamentList = new ArrayList<>();
            for (SerializableTournament _st : st) {
                tournamentList.add(new Tournament(_st));
            }

            tournamentListOG = (ArrayList<Tournament>) tournamentList.clone();
            System.out.println(tournamentListOG.size());
            launch();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ArrayList<Tournament> getTournamentList() {
        return tournamentListOG;
    }

    // Method generates a unique id which is different from all ids in the
    // tournament list
    public static int generateUniqueID() {
        ArrayList<Integer> idArray = new ArrayList<>();
        for (Tournament e : tournamentListOG) {
            idArray.add(e.getid());
        }

        Random r = new Random();
        int randomID = r.nextInt(0, 10000);
        while (idArray.contains(randomID)) {
            randomID = r.nextInt(0, 10000);
        }
        return randomID;

    }

    public static void saveTournaments(String fileName) {
        try (FileOutputStream outFile = new FileOutputStream(fileName);
                ObjectOutputStream outObj = new ObjectOutputStream(outFile);) {
            ArrayList<SerializableTournament> t = new ArrayList<>();
            for (Tournament tour : tournamentListOG) {
                t.add(new SerializableTournament(tour));
            }
            Compressor c = new Compressor(t);
            outObj.writeObject(c);
            System.out.println("saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}