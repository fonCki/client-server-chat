package shared.util;

import java.beans.PropertyChangeListener;

public interface Subject {
    public void addListener(String evt, PropertyChangeListener listener);
    public void removeListener(String evt, PropertyChangeListener listener);
}
