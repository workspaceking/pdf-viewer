package studyapp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import studyapp.helpers.HDir;

/**
 * 
 * @author Rashid Iqbal
 * @email v3rsion9@gmail.com
 * @github https://github.com/rashidcoder
 * 
 */

public class App extends Application {

    private Parent app_UI;
    private Scene scene;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        try {
            HDir.createTreeDir();
            setApp_UI(FXMLLoader.load(getClass().getResource("/studyapp/views/app.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setApp_UI(Parent parent) {
        app_UI = parent;
        scene = new Scene(this.app_UI);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for (File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }

}
