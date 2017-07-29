package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EActionMode;

import java.io.Serializable;

/**
 * Created by Nils on 11.06.2017.
 */
public class ActionModeBean implements Serializable{
    private EActionMode actionMode_Enum;
    private String actionMode_String = "";

    public ActionModeBean(EActionMode actionMode_Enum) {
        this.actionMode_Enum = actionMode_Enum;
        lookUpString();
    }

    public EActionMode getActionMode_Enum() {
        return actionMode_Enum;
    }

    public String getActionMode_String() {
        return actionMode_String;
    }

    private void lookUpString(){
        switch (actionMode_Enum){
            case DAYMODE:
                actionMode_String = "Daymode";
                break;
            case NIGHTMODE:
                actionMode_String = "Nightmode";
                break;
            case MAINTENANCEMODE:
                actionMode_String = "Maintenancemode";
                break;
            case NA:
                actionMode_String = "N/A";
                break;
        }
    }
}
