package com.sakuya.hangup.modules.player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerPropertyChangeListener implements PropertyChangeListener {
    private String uuid;

    public PlayerPropertyChangeListener(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
