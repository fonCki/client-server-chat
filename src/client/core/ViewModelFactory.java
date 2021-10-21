package client.core;

import client.views.main.MainViewModel;
import client.views.settingsView.SettingsViewModel;
import client.views.tabView.TabViewModel;

public class ViewModelFactory {
    private MainViewModel mainViewModel;
    private SettingsViewModel settingsViewModel;
    private ModelFactory modelFactory;
    private TabViewModel tabViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
        this.mainViewModel = new MainViewModel(modelFactory.getMessageModel());
        this.settingsViewModel = new SettingsViewModel(modelFactory.getMessageModel());
        this.tabViewModel = new TabViewModel(modelFactory.getMessageModel());

    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public SettingsViewModel getSettingsViewModel() {
        return settingsViewModel;
    }

    public TabViewModel getTabViewModel() {
        return tabViewModel;
    }
}
