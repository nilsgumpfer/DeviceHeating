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

    public ActionModeBean(String actionMode){
        switch (actionMode){
            case "DAYMODE":
                actionMode_Enum = EActionMode.DAYMODE;
                break;
            case "NIGHTMODE":
                actionMode_Enum = EActionMode.NIGHTMODE;
                break;
            case "MAINTENANCEMODE":
                actionMode_Enum = EActionMode.MAINTENANCEMODE;
                break;
            case "STANDARDMODE":
                actionMode_Enum = EActionMode.STANDARDMODE;
                break;
            case "FAHRENHEIT":
                actionMode_Enum = EActionMode.FAHRENHEIT;
                break;
            case "CELSIUS":
                actionMode_Enum = EActionMode.CELSIUS;
                break;
            case "ANGLOAMERICAN":
                actionMode_Enum = EActionMode.ANGLOAMERICAN;
                break;
            case "METRIC":
                actionMode_Enum = EActionMode.METRIC;
                break;
            case "NA":
                actionMode_Enum = EActionMode.NA;
                break;
        }

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
            case STANDARDMODE:
                actionMode_String = "Standardmode";
                break;
            case FAHRENHEIT:
                actionMode_String = "Fahrenheit";
                break;
            case CELSIUS:
                actionMode_String = "Celsius";
                break;
            case ANGLOAMERICAN:
                actionMode_String = "Angloamerican";
                break;
            case METRIC:
                actionMode_String = "Metric";
                break;
            case NA:
                actionMode_String = "N/A";
                break;
        }
    }
}
