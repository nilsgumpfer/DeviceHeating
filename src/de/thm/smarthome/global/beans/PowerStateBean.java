package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EPowerState;

import java.io.Serializable;

/**
 * Created by Nils on 11.06.2017.
 */
public class PowerStateBean implements Serializable{
    private EPowerState powerState_Enum;
    private String powerState_String = "NULL";

    public PowerStateBean(EPowerState powerState_Enum) {
        this.powerState_Enum = powerState_Enum;
        lookUpString();
    }

    public PowerStateBean(String powerState) {
        switch (powerState) {
            case "NA":
                powerState_Enum = EPowerState.NA;
                break;
            case "ON":
                powerState_Enum = EPowerState.ON;
                break;
            case "OFF":
                powerState_Enum = EPowerState.OFF;
                break;
        }

        lookUpString();
    }

    public PowerStateBean(boolean powerState) {
        if(powerState)
            powerState_Enum = EPowerState.ON;
        else
            powerState_Enum = EPowerState.OFF;

        lookUpString();
    }

    public EPowerState getPowerState_Enum() {
        return powerState_Enum;
    }

    public String getPowerState_String() {
        return powerState_String;
    }

    private void lookUpString(){
        switch (powerState_Enum){
            case NA:
                powerState_String = "N/A";
                break;
            case ON:
                powerState_String = "Eingeschaltet";
                break;
            case OFF:
                powerState_String = "Ausgeschaltet";
                break;

        }
    }

    public boolean getPowerState_Boolean() {
        if(powerState_Enum == EPowerState.ON)
            return true;
        else
            return false;
    }
}
