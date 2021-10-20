package client.core;

import client.views.main.MainViewModel;
import client.views.privateView.PrivateViewModel;
import client.views.settingsView.SettingsViewModel;
import client.views.tabView.TabViewModel;
import client.views.users.UsersViewModel;

public class ViewModelFactory {
    private MainViewModel mainViewModel;
    private UsersViewModel usersViewModel;
    private SettingsViewModel settingsViewModel;
    private PrivateViewModel privateViewModel;
    private ModelFactory modelFactory;
    private TabViewModel tabViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.mainViewModel = new MainViewModel(modelFactory.getMessageModel());
        this.usersViewModel = new UsersViewModel(modelFactory.getMessageModel());
        this.settingsViewModel = new SettingsViewModel(modelFactory.getMessageModel());
        this.privateViewModel = new PrivateViewModel(modelFactory.getMessageModel());
        this.tabViewModel = new TabViewModel(modelFactory.getMessageModel());
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

    public TabViewModel getTabViewModel() {
        return tabViewModel;
    }
}
