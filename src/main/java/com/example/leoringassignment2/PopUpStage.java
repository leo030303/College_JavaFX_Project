package com.example.leoringassignment2;

import javafx.stage.Stage;

import java.util.Objects;

/**
 * A class to add some functionality to the stage for confirmation boxes
 * @author Leo Ring
 */
public class PopUpStage extends Stage {
    private Boolean confirmation; // The confirmation field is to return whether the user chose yes or no
    private final String styleSheet; // The stylesheet field is just so you can access the stylesheet resource in a static context

    /**
     * constructs new stage
     */
    public PopUpStage(){
        this.confirmation = false;
        this.styleSheet = Objects.requireNonNull(this.getClass().getResource("assignment1Stylesheet.css")).toExternalForm();
    }

    /**
     *
     * @param newConfirmation new confirmation value
     */
    public void setConfirmation(Boolean newConfirmation){this.confirmation = newConfirmation;}

    /**
     *
     * @return boolean of whether user chose yes or not
     */
    public Boolean getConfirmation(){return this.confirmation;}

    /**
     *
     * @return stylesheet for project
     */
    public String getStyleSheet(){return this.styleSheet;}

    /**
     * shows pop up
     * @return boolean whether user chose yes or not
     */
    public Boolean showPopUp() {
        super.showAndWait();
        return getConfirmation();
    }
}

