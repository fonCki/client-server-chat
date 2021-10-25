package client.core;

import client.views.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import shared.transferobjects.Message;
import shared.transferobjects.User;
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
        Scene scene = new Scene(getRoot("../views/mainView/Main.fxml", null));
        stage.setScene(scene);
        stage.show();
    }

    public void openSettingsView() {
        stage.setTitle("Welcome");
        Scene scene = new Scene(getRoot("../views/settingsView/Settings.fxml", null));
        stage.setScene(scene);
        stage.show();
    }


    public void openTabView(Tab tab, User receiver, Message initMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/tabView/Tab.fxml"));
            tab.setContent(loader.load());
            ViewController viewController = loader.getController();
            viewController.init(this, viewModelFactory, stage, receiver, initMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Parent getRoot(String path, User userSelected) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            root = loader.load();
            ViewController viewController = loader.getController();
            viewController.init(this, viewModelFactory, stage, userSelected, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

}
