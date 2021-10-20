package client.views.main;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import client.views.main.tools.TabList;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;


public class MainViewController implements ViewController {
    ViewHandler viewHandler;
    MainViewModel mainViewModel;
    private TabList tabList;


    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<String, User> nickNameTableColumn;
    @FXML
    private TabPane tabPane;

    SingleSelectionModel<Tab> selectionModel;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver, Message initMessage) {
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModelFactory.getMainViewModel();
        tabList = new TabList();

        mainViewModel.loadOnlineUsers();
        usersTableView.setItems(mainViewModel.getUsers());
        nickNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));

        selectionModel = tabPane.getSelectionModel();

        mainViewModel.addListener("CREATE_NEW_TAB", this::checkNewTab);

        //Create the main tab
        Tab mainTab = new Tab("ALL");
        tabPane.getTabs().add(mainTab);
        viewHandler.openTabView(mainTab, null, null);
        mainTab.closableProperty().setValue(false);
        //


        stage.setTitle(mainViewModel.getIdentity().getNickName());



        usersTableView.setRowFactory(userTableView -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    User userSelected = row.getItem();
                    if (!(userSelected.getID().equals(mainViewModel.getIdentity().getID()))) {

                        if (!(tabList.existTab(userSelected.getID()))) {

                            tabList.addTab(userSelected);
                            tabPane.getTabs().add(tabList.getTab(userSelected.getID()));
                            viewHandler.openTabView(tabList.getTab(userSelected.getID()), userSelected, null);
                        } else {
                            selectionModel.select(tabList.getTab(userSelected.getID()));
                        }
                        tabList.getTab(userSelected.getID()).setOnCloseRequest(event -> {
                            tabList.removeTab(userSelected.getID());
                            System.out.println("removed");

                        });


                    }
                }
            });
            return row;
        });


        stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                mainViewModel.userLeft();
                Platform.exit();
                System.exit(0);
            }
        }));
    }

    private void checkNewTab(PropertyChangeEvent event) {
        Message newMessage = (Message) event.getNewValue();
        System.out.println(newMessage);
        User userSelected = newMessage.getSender();
        if (!(userSelected.getID().equals(mainViewModel.getIdentity().getID()))) {

            if (!(tabList.existTab(userSelected.getID()))) {
                Platform.runLater(() -> createTab(userSelected, newMessage)
            );
            } else {
                selectionModel.select(tabList.getTab(userSelected.getID()));
                System.out.println("Hpla");
            }

        }
    }

    private void createTab(User userSelected, Message newMessage) {
        tabList.addTab(userSelected);
        tabPane.getTabs().add(tabList.getTab(userSelected.getID()));
        viewHandler.openTabView(tabList.getTab(userSelected.getID()), userSelected, newMessage);
        tabList.getTab(userSelected.getID()).setOnCloseRequest(evt -> {
            tabList.removeTab(userSelected.getID());
            System.out.println("Removed 2");
        });
    }
}