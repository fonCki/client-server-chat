package client.views;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import javafx.stage.Stage;
import shared.transferobjects.User;

public interface ViewController {
     void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver);
}
