package client.core;

import client.core.designControl.PrivateStage;
import client.views.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewHandler {
    private ViewModelFactory viewModelFactory;
    private Stage stage;
    private Parent root;

    private List<PrivateStage> privateStageList;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        privateStageList = new ArrayList<PrivateStage>();
        this.viewModelFactory = viewModelFactory;
        this.stage = new Stage();
    }

    public void openMainView(){
        //stage.setTitle("Main");
        Scene scene = new Scene(getRoot("../views/main/newMain.fxml", null));
        stage.setScene(scene);
        stage.show();
    }

    public void openSettingsView() {
        stage.setTitle("Settings");
        Scene scene = new Scene(getRoot("../views/settingsView/Settings.fxml", null));
        stage.setScene(scene);
        stage.show();
    }

    public void openUsersView() {
     //   Stage stage1 = new Stage();
    //    stage1.setTitle("Users");
    //    Scene scene2 = new Scene(getRoot("../views/users/Users.fxml"));
     //   stage1.setScene(scene2);
     //   stage1.show();
    }

        /*
    public void openTabView(Tab tab) {
        try {
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("../views/main/Tab.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    */

    public void openTabView(Tab tab, User receiver, Message initMessage) {

        try {

            //FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.load(getClass().getResource("../views/main/Tab.fxml"));
            //ViewController viewController = (ViewController) fxmlLoader.getController();
          //  tab.setContent((Node) getRoot("../views/main/Tab.fxml",null));
          //  viewController.init(viewHandler, viewModelFactory, stage, null);

            //...


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

    public void openPrivateView(User userSelected) {
        PrivateStage tempStage = privateStageList.stream().filter(privateStage ->
                userSelected.getID().equals(privateStage.getID())).findFirst().orElse(null);
        if (tempStage == null) {
            tempStage = new PrivateStage(userSelected.getID(), new Stage());
            privateStageList.add(tempStage);
            System.out.println(privateStageList.size());
        }

       // if (tempStage.getStage() == null) {
            tempStage.getStage().setTitle("Private chat with " + userSelected.getNickName());
            Scene scene2 = new Scene(getRoot("../views/privateView/Private.fxml", userSelected));
            tempStage.getStage().setScene(scene2);
       // }
        tempStage.getStage().show();
        tempStage.getStage().setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
         //       finalTempStage.getStage() = null;
            }
        }));

    }



/*
        if (stage1 == null) {
            stage1
            stage1.setTitle("Private");
            Scene scene2 = new Scene(getRoot("../views/privateView/Private.fxml", userSelected));
            stage1.setScene(scene2);
            stage1.show();
            stage1.setOnCloseRequest((new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    stage1 = null;
                }
            }));
        }

    }

 */
}
