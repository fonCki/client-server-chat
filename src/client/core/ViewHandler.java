package client.core;

import client.views.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private Stage stage;
    private Parent root;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        this.stage = new Stage();
    }

    public void openMainView(){
        stage.setTitle("Main");
        Scene scene = new Scene(getRoot("../views/main/Main.fxml"));
        stage.setScene(scene);
        stage.show();
    }

    public void openSettingsView() {
        stage.setTitle("Settings");
        Scene scene = new Scene(getRoot("../views/settingsView/Settings.fxml"));
        stage.setScene(scene);
        stage.show();
    }

    public void openUsersView() {
        Stage stage1 = new Stage();
        stage1.setTitle("Users");
        Scene scene2 = new Scene(getRoot("../views/users/Users.fxml"));
        stage1.setScene(scene2);
        stage1.show();
    }

    private Parent getRoot(String path) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            ViewController viewController = loader.getController();
            viewController.init(this, viewModelFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
