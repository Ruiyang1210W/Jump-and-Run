package util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ObservableModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public void addListener(PropertyChangeListener l){ pcs.addPropertyChangeListener(l); }
    public void removeListener(PropertyChangeListener l){ pcs.removePropertyChangeListener(l); }
    protected void fireChange(String prop, Object oldVal, Object newVal){ pcs.firePropertyChange(prop, oldVal, newVal); }
}
