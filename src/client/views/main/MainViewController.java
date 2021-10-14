package client.views.main;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import shared.transferobjects.User;

public class MainViewController implements ViewController {
    ViewHandler viewHandler;
    MainViewModel mainViewModel;

    @FXML private TextArea messageTextArea;
    @FXML private TextField messageTextField;
    @FXML private TableView<User> usersTableView;
    @FXML private TableColumn<String, User> usersTableColumn;
    @FXML private Button onSendButton;

    @FXML TabPane tabPane;
    @Override

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver) {
        tabPane = new TabPane();
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModelFactory.getMainViewModel();
        mainViewModel.loadOnlineUsers();
        usersTableView.setItems(mainViewModel.getUsers());
        usersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        messageTextArea.textProperty().bind(mainViewModel.messageProperty());
        onSendButton.disableProperty().bind(Bindings.isEmpty(messageTextField.textProperty()));
        tabPane.setSide(Side.LEFT);


        stage.setTitle(mainViewModel.getIdentity().getNickName());

        usersTableView.setRowFactory(userTableView -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (!row.isEmpty())) {
                    User userSelected = row.getItem();
                    if (!(userSelected.getID().equals(mainViewModel.getIdentity().getID())))
                        viewHandler.openPrivateView(userSelected);
                }
            });
            return row;
        });



        messageTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onSend(null);
            }
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

    public void onSend(ActionEvent actionEvent) {
        if (!(messageTextField.getText() == null)  &&
                (!(messageTextField.getText() == "")))
        mainViewModel.sendMessage(messageTextField.getText());
        messageTextField.clear();
    }
}
