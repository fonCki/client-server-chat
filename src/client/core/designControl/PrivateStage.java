package client.core.designControl;

import javafx.stage.Stage;

public class PrivateStage {
    private String ID;
    private Stage stage;

    public PrivateStage(String ID, Stage stage) {
        this.ID = ID;
        this.stage = stage;
    }

    public String getID() {
        return ID;
    }

    public Stage getStage() {
        return stage;
    }

}
