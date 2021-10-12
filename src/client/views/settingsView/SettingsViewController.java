package client.views.settingsView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SettingsViewController implements ViewController {
    private SettingsViewModel settingsViewModel;
    private ViewHandler viewHandler;
    @FXML private TextField nickNameTextField;


    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.settingsViewModel = viewModelFactory.getSettingsViewModel();
        this.viewHandler = viewHandler;
        nickNameTextField.textProperty().bindBidirectional(settingsViewModel.nickNameProperty());
    }

    public void onOkButton(ActionEvent actionEvent) {
        if (!nickNameTextField.textProperty().equals("")) {
            settingsViewModel.onNewUser();
            viewHandler.openMainView();
        }
    }

}
