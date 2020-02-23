/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyapp.controllers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import studyapp.App;
import studyapp.PdfWidget;
import studyapp.data.PDF;

/**
 *
 * @author Rashid Iqbal
 * @email v3rsion9@gmail.com
 * @github https://github.com/rashidcoder
 *
 */
public class AppController implements Initializable {

    @FXML
    private BorderPane pdfContainer;

    @FXML
    private Button browseDirectory;

    @FXML
    private Accordion contentView;

    @FXML
    private TitledPane pdfView;

    @FXML
    private TitledPane video;

    @FXML
    private TreeView<String> fileExplorer;

    Map<String, String> corespath = new HashMap<>();
    static String url = "https://www.google.com";
    static String selectedFile = "";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        fileExplorer.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) -> {
            TreeItem<String> selectedItem;
            selectedItem = (TreeItem<String>) newValue;
            corespath.entrySet().forEach((Map.Entry<String, String> entry) -> {
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (key.toString().contains(".pdf") && key.toString().contains(selectedItem.getValue())) {
                    System.out.println("file : " + key);
                    System.out.println("path : " + val);
                    selectedFile = val.toString();
                    url = val.toString().replace("\\", "/");

                }
            });

            if (selectedItem.getValue().contains(".pdf")) {
                PDF.PATH = url;
                try {
//                        Stage secondStage = new Stage();
//                        secondStage.sizeToScene();
//                        secondStage.centerOnScreen();
                    Scene _scene = new PdfWidget().start(url);
                    pdfView.setContent(_scene.getRoot());
                    pdfView.expandedProperty().set(true);
//                    pdfContainer.setCenter(_scene.getRoot());
//                        secondStage.show(); 

//                        new PdfFXViewer().start(App.mainStage);
                } catch (Exception ex) {
                    Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Selected Text : " + url);

            }
        });
        fileExplorer.setRoot(getNodesForDirectory(new File("E:\\data")));

    }

    @FXML
    void browserDirectory(ActionEvent event) {

        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        File choice = dc.showDialog(App.mainStage);
        if (choice == null || !choice.isDirectory()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Could not open directory");
            alert.setContentText("The file is invalid.");
            alert.showAndWait();
        } else {
            fileExplorer.setRoot(getNodesForDirectory(new File("E:\\data")));
        }
    }

    public TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<>(directory.getName());
        for (File f : directory.listFiles()) {
            System.out.println("Loading " + f.getName());
            if (f.isDirectory()) { //Then we call the function recursively
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                corespath.put(f.getName(), f.getAbsolutePath());
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }
        return root;
    }

}
