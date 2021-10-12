package client.views.users;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.transferobjects.User;

public class UsersViewController implements ViewController {
    UsersViewModel usersViewModel;

    @FXML
    private TableView<User> usersTableView;
    @FXML private TableColumn<String, User> usersTableColumn;

    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        //this.usersViewModel = usersViewModel;
       // usersViewModel.loadOnlineUsers();
        //usersTableView.setItems(usersViewModel.getOnlineUsers());
       // usersTableColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));

    }
}
