package io.github.stekeblad.videouploader.youtube;

import io.github.stekeblad.videouploader.youtube.utils.VisibilityStatus;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Represents a preset.
 */
public class VideoPreset extends VideoInformationBase {

    public static final String NODE_ID_PRESETNAME = "_presetName";
    public static final String NODE_ID_BUTTONSBOX = "_buttons";

    private GridPane presetPane;

    /**
     * @return returns the name of this preset
     */
    public String getPresetName() {
        return ((TextField) presetPane.lookup("#" + getPaneId() + NODE_ID_PRESETNAME)).getText();
    }

    /**
     * @return returns the entire UI pane for placement on screen
     */
    public GridPane getPresetPane() {
        return presetPane;
    }

    /**
     * @return returns the Id of the button in the first button slot. Can be used to know what button is there at the moment
     */
    public String getButton1Id() {
        return ((HBox) presetPane.lookup("#" + getPaneId() + NODE_ID_BUTTONSBOX)).getChildren().get(0).getId();
    }

    /**
     * @return returns the Id of the button in the second button slot. Can be used to know what button is there at the moment
     */
    public String getButton2Id() {
        return ((HBox) presetPane.lookup("#" + getPaneId() + NODE_ID_BUTTONSBOX)).getChildren().get(1).getId();
    }

    /**
     * Enables / Disables editing of all fields on the pane
     * @param newEditStatus true to allow edit, false to not allow
     */
    public void setEditable(boolean newEditStatus) {
        super.setEditable(newEditStatus);
        ((TextField) presetPane.lookup("#" + getPaneId() + NODE_ID_PRESETNAME)).setEditable(newEditStatus);
    }

    /**
     * Place a button in the first button slot with your own text, click behavior etc.
     * @param btn1 A fully configured button
     */
    public void setButton1(Button btn1) {
        ((HBox) presetPane.lookup("#" + getPaneId() + NODE_ID_BUTTONSBOX)).getChildren().set(0, btn1);
    }

    /**
     * Place a button in the second button slot with your own text, click behavior etc.
     * @param btn2 A fully configured button
     */
    public void setButton2(Button btn2) {
        ((HBox) presetPane.lookup("#" + getPaneId() + NODE_ID_BUTTONSBOX)).getChildren().set(1, btn2);
    }

    /**
     * Constructor for VideoPreset. There is also the VideoPreset.Builder class if that is preferred.
     * Everything set here can be edited later except the paneId
     * @param videoName Title for the the videos that uses this preset
     * @param videoDescription Description for the videos that uses this preset
     * @param visibility The visibility status that will be used for the video that uses this status
     * @param videoTags The tags that will be assigned to the videos that uses this preset
     * @param playlist The name of the playlist that will be the selected one for the videos that uses this preset
     * @param category The name of the category to be selected  for the videos that uses this preset
     * @param tellSubs Set to true if subscribers should be notified when videos using this preset is uploaded, set to false to not notify
     * @param thumbNailPath File path to a thumbnail to use for all videos that uses this preset or null to let thumbnail be selected automatically
     * @param paneId A string used for naming all UI elements
     * @param presetName A name used to recognize this preset
     */
    public VideoPreset(String videoName, String videoDescription, VisibilityStatus visibility, List<String> videoTags,
                       String playlist, String category, boolean tellSubs, String thumbNailPath, String paneId, String presetName) {
        super(videoName, videoDescription, visibility, videoTags, playlist, category, tellSubs, thumbNailPath, paneId);
        makePresetPane(presetName);
    }

    /**
     * Reconstructs a VideoPreset form its string version created by calling toString()
     * @param fromString The string representation of a VideoPreset
     * @param paneId A string used for naming all UI elements
     * @throws Exception If the string could not be converted to a VideoPreset
     */
    public VideoPreset(String fromString, String paneId) throws Exception {
        super(fromString, paneId);

        String presetName = null;

        String[] lines = fromString.split("\n");
        for (String line : lines) {
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                switch (line.substring(0, colonIndex)) {
                    case NODE_ID_PRESETNAME:
                        presetName = line.substring(colonIndex + 1);
                        break;
                    default:
                        // likely belong to parent
                }
            }
        }
        if (presetName == null) {
            throw new Exception("String representation of class does not have presetName");
        }
        makePresetPane(presetName);
    }

    /**
     * Creates a copy of this VideoPreset with the same or different paneId.
     * @param paneIdForCopy The paneId used for naming the nodes in the copy, use null to get the same as original.
     *                      if null is given then the original and the copy may not be able to be on screen at the same time,
     *                      the nodes will be considered to be the same and when placing the second one of them it will cause the
     *                      nodes from the first to be moved to the location of the second.
     * @return a copy of this VideoPreset
     */
    public VideoPreset copy(String paneIdForCopy) {
        if(paneIdForCopy == null) {
            paneIdForCopy = this.getPaneId();
        }
        String thumbnailPath;
        if(getThumbNail() == null) {
            thumbnailPath = null;
        } else {
            thumbnailPath = getThumbNail().getAbsolutePath();
        }
        return new VideoPreset(getVideoName(), getVideoDescription(), getVisibility(), getVideoTags(), getPlaylist(),
                getCategory(), isTellSubs(), thumbnailPath, paneIdForCopy, getPresetName());
    }

    /**
     * Used for building a VideoPreset one attribute at the time.
     * Call build() to get a real VideoPreset when you are done setting attributes.
     */
    public static class Builder extends VideoInformationBase.Builder{
        String presetName;

        public String getPresetName() {
            return presetName;
        }

        public VideoPreset.Builder setPresetName(String presetName) {
            this.presetName = presetName;
            return this;
        }
        
        // Re-implementation of setters in super to get the right return type
        public VideoPreset.Builder setVideoName(String videoName) {
            this.videoName = videoName;
            return this;
        }

        public VideoPreset.Builder setVideoDescription(String videoDescription) {
            this.videoDescription = videoDescription;
            return this;
        }

        public VideoPreset.Builder setVisibility(VisibilityStatus visibility) {
            this.visibility = visibility;
            return this;
        }

        public VideoPreset.Builder setVideoTags(List<String> videoTags) {
            this.videoTags = videoTags;
            return this;
        }

        public VideoPreset.Builder setPlaylist(String playlist) {
            this.playlist = playlist;
            return this;
        }

        public VideoPreset.Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public VideoPreset.Builder setTellSubs(boolean tellSubs) {
            this.tellSubs = tellSubs;
            return this;
        }

        public VideoPreset.Builder setThumbNailPath(String thumbNailPath) {
            this.thumbNailPath = thumbNailPath;
            return this;
        }

        public VideoPreset.Builder setPaneName(String paneName) {
            this.paneName = paneName;
            return this;
        }

        public VideoPreset build() {
            return new VideoPreset(getVideoName(), getVideoDescription(), getVisibility(), getVideoTags(), getPlaylist(),
                    getCategory(), isTellSubs(), getThumbNailPath(), getPresetName(), presetName);
        }
    }

    /**
     * Creates the UI Pane so it can be be retrieved by front end code with getPresetPane()
     * @param name The preset name
     */
    protected void makePresetPane(String name) {
        // The base class has already done most of the work
        presetPane = super.getPane();
        // make the pane slightly larger for the extra information to fit
        presetPane.setPrefHeight(170);

        TextField presetName = new TextField();
        presetName.setId(getPaneId() + NODE_ID_PRESETNAME);
        presetName.setPromptText("Preset name");
        presetName.setText(name);
        presetName.setEditable(false);

        Button ghostBtn1 = new Button("");
        ghostBtn1.setVisible(false);
        Button ghostBtn2 = new Button("");
        ghostBtn2.setVisible(false);
        HBox buttonsBox = new HBox(5, ghostBtn1, ghostBtn2);
        buttonsBox.setId(getPaneId() + NODE_ID_BUTTONSBOX);

        presetPane.add(presetName, 0, 4);
        presetPane.add(buttonsBox, 1, 4);
    }

    /**
     * Creates a string representation of the class that can be saved and later used to recreate the class as it
     * looked like before with the VideoPreset(String, String) constructor
     * @return A String representation of this class
     */
    public String toString() {
        return super.toString() + "\n" +
                NODE_ID_PRESETNAME + ":" + getPresetName();
    }
}