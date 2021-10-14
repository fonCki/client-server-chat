package client.core;

import client.views.main.MainViewModel;
import client.views.privateView.PrivateViewController;
import client.views.privateView.PrivateViewModel;
import client.views.settingsView.SettingsViewModel;
import client.views.users.UsersViewModel;

public class ViewModelFactory {
    private MainViewModel mainViewModel;
    private UsersViewModel usersViewModel;
    private SettingsViewModel settingsViewModel;
    private PrivateViewModel privateViewModel;
    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.mainViewModel = new MainViewModel(modelFactory.getMessageModel());
        this.usersViewModel = new UsersViewModel(modelFactory.getMessageModel());
        this.settingsViewModel = new SettingsViewModel(modelFactory.getMessageModel());
        this.privateViewModel = new PrivateViewModel(modelFactory.getMessageModel());
        this.modelFactory = modelFactory;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public UsersViewModel getUsersViewModel() {
        return usersViewModel;
    }

    public SettingsViewModel getSettingsViewModel() {
        return settingsViewModel;
    }

    public PrivateViewModel getPrivateViewModel() {
        return privateViewModel;
    }
}
