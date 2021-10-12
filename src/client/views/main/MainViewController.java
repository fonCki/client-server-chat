package client.views.main;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.transferobjects.User;

public class MainViewController implements ViewController {
    ViewHandler viewHandler;
    MainViewModel mainViewModel;

    @FXML private TextArea messageTextArea;
    @FXML private TextField messageTextField;
    @FXML private TableView<User> usersTableView;
    @FXML private TableColumn<String, User> usersTableColumn;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.mainViewModel = viewModelFactory.getMainViewModel();
        mainViewModel.loadOnlineUsers();
        usersTableView.setItems(mainViewModel.getOnlineUsers());
        usersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        messageTextArea.textProperty().bind(mainViewModel.messageProperty());
    }

    public void onSend(ActionEvent actionEvent) {
        mainViewModel.sendMessage(messageTextField.getText());
        messageTextField.clear();
    }
}
