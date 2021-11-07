package client.views.mainView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import client.views.mainView.tools.TabList;
import client.views.mainView.tools.UsersSimplified;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.transferobjects.Message;
import shared.transferobjects.User;

import java.beans.PropertyChangeEvent;


public class MainViewController implements ViewController {
    ViewHandler viewHandler;
    MainViewModel mainViewModel;
    private TabList tabList;


    @FXML private TableView<UsersSimplified> usersTableView;
    @FXML private TableColumn<UsersSimplified, String> nickNameTableColumn;
    @FXML private TableColumn<UsersSimplified, Circle> avatarTableColumn;
    @FXML private TabPane tabPane;
    @FXML private Circle avatarCircle;
    @FXML private Text nickNameText;

    SingleSelectionModel<Tab> selectionModel;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver, Message initMessage) {
        //Initialize
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModelFactory.getMainViewModel();
        tabList = new TabList();
        mainViewModel.loadOnlineUsers();
        usersTableView.setItems(mainViewModel.getSimplifiedUsers());
        for (UsersSimplified user: mainViewModel.getSimplifiedUsers()) {
            System.out.println(user);
        }

        avatarTableColumn.setCellValueFactory(new PropertyValueFactory<>("circle"));
        nickNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));



        selectionModel = tabPane.getSelectionModel();

        //Listen
        mainViewModel.addListener("CREATE_NEW_TAB", this::checkNewTab);

        //Create the main tab
        Tab mainTab = new Tab("ALL");
        tabPane.getTabs().add(mainTab);
        viewHandler.openTabView(mainTab, null, null);
        mainTab.closableProperty().setValue(false);
        //


        //set the design elements
        stage.setTitle("Welcome back " + mainViewModel.getIdentity().getNickName());
        nickNameText.setText(mainViewModel.getIdentity().getNickName());
        avatarCircle.setFill(new ImagePattern(mainViewModel.getIdentity().getAvatar()));


        //set the user's table selection
        usersTableView.setRowFactory(userTableView -> {
            TableRow<UsersSimplified> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    UsersSimplified userSelected = row.getItem();
                    if (!(userSelected.getID().equals(mainViewModel.getIdentity().getID()))) {
                        if (!(tabList.existTab(userSelected.getID()))) {
                            for (User user: mainViewModel.getUserList()) {
                                if (userSelected.getID().equals(user.getID())) {
                                      createTab(user, null);
                                      break;
                                }
                            }
                            selectionModel.select(tabList.getTab(userSelected.getID()));
                        }
                    }
                }
            });
            return row;
        });

        //set the close event
        stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                mainViewModel.userLeft();
                Platform.exit();
                System.exit(0);
            }
        }));
    }

    // check new tab
    private void checkNewTab(PropertyChangeEvent event) { // THIS METHOD IS EQUAL TO DOUBLE CLICK EVENT, JOIN THEM!
        Message newMessage = (Message) event.getNewValue();
        User userSelected = newMessage.getSender();
        if (!(userSelected.getID().equals(mainViewModel.getIdentity().getID()))) {
            if (!(tabList.existTab(userSelected.getID()))) {
                Platform.runLater(() -> createTab(userSelected, newMessage)
            );
            } else {
                selectionModel.select(tabList.getTab(userSelected.getID()));
            }

        }
    }


    // create new tab
    private void createTab(User userSelected, Message newMessage) {
        tabList.addTab(userSelected);
        tabPane.getTabs().add(tabList.getTab(userSelected.getID()));
        viewHandler.openTabView(tabList.getTab(userSelected.getID()), userSelected, newMessage);
        tabList.getTab(userSelected.getID()).setOnCloseRequest(evt -> {
            tabList.removeTab(userSelected.getID());
        });
    }
}