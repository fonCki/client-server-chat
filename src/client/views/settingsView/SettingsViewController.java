package client.views.settingsView;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import shared.transferobjects.User;

public class SettingsViewController implements ViewController {
    private SettingsViewModel settingsViewModel;
    private ViewHandler viewHandler;
    @FXML private TextField nickNameTextField;
    @FXML private Button onOkButton;


    @Override
    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Stage stage, User receiver) {
        this.settingsViewModel = viewModelFactory.getSettingsViewModel();
        this.viewHandler = viewHandler;
        nickNameTextField.textProperty().bindBidirectional(settingsViewModel.nickNameProperty());

        onOkButton.disableProperty().bind(Bindings.isEmpty(nickNameTextField.textProperty()));
        nickNameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onOkButton(null);
            }
        });
    }

    public void onOkButton(ActionEvent actionEvent) {
        if (!nickNameTextField.textProperty().equals("")) {
            settingsViewModel.newUser();
            viewHandler.openMainView();
        }
    }

}
