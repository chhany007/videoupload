package io.github.stekeblad.youtubeuploader.main;

import io.github.lilahamstern.AlertBox;
import io.github.stekeblad.youtubeuploader.utils.ConfigManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    public Button buttDoThing;
    public ListView<Pane> listView;
    public GridPane videoGridPane;
    public Button buttonPickFile;
    public Button buttonAddFile;
    public ListView<String> chosen_files;
    public TextField txt_common_title;
    public TextArea txt_common_description;
    public TextField txtStartEpisode;
    public TextArea txtTags;
    public TextField txt_playlistURL;
    public Button btn_presets;
    public ChoiceBox choice_presets;
    public AnchorPane mainWindowPane;

    private ConfigManager configManager;
    private int videoPaneCounter;
    private List<Pane> videoPanes;

    public mainController() {
        configManager = new ConfigManager();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        videoPaneCounter = 0;
        videoPanes = new ArrayList<>();
        configManager = new ConfigManager();
        if (configManager.getNoSettings()) {
            AlertBox.display("No settings found", "Go to settings and add some");
            onSettingsPressed(new ActionEvent());
            configManager.setNoSettings(false);
            configManager.saveSettings();
        }

        txtStartEpisode.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtStartEpisode.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });




        //testImageView.setImage(tesetImage);
    }

    public void onDoThingClicked(ActionEvent event) {

        event.consume();
    }


    public void onPickFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a video file to upload");
        Stage fileChooserStage = new Stage();
        List<File> filesToUpload = fileChooser.showOpenMultipleDialog(fileChooserStage);
        if (filesToUpload != null) {

            List<String> filenames = new ArrayList<>();
            for (int i = 0; i < filesToUpload.size(); i++) {
                String fileNameString = filesToUpload.get(i).getName();
                filenames.add(fileNameString);
            }
            chosen_files.setItems(FXCollections.observableArrayList(filenames));
        }

        actionEvent.consume();
    }

    public void onAddUploads(ActionEvent actionEvent) {
        /*if(chosen_files.getItems().size() == 0) {
            return;
        }
        GridPane newVideo = PaneFactory.makeUploadPane("vid" + videoPaneCounter);
        ((TextField) newVideo.lookup("#vid" + videoPaneCounter + "_title")).setText(nameOfFile.getText());
        videoPanes.add(newVideo);
        nameOfFile.setText("");
        listView.setItems(FXCollections.observableArrayList(videoPanes));
        videoPaneCounter++;
        actionEvent.consume();
*/
    }

    public void onSettingsPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(mainController.class.getClassLoader().getResource("fxml/SettingsWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 725, 700);
            Stage stage = new Stage();
            stage.setTitle("Settings - Stekeblads Youtube Uploader");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        actionEvent.consume();
    }
}
