package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EUnitOfMeasurement;

/**
 * Created by Nils on 11.06.2017.
 */
public class UnitOfMeasurementBean {
    private EUnitOfMeasurement unitOfMeasurement_Enum;
    private String unitOfMeasurement_String = "NULL";

    public UnitOfMeasurementBean(EUnitOfMeasurement unitOfMeasurement_Enum) {
        this.unitOfMeasurement_Enum = unitOfMeasurement_Enum;
        lookUpString();
    }

    public EUnitOfMeasurement getUnitOfMeasurement_Enum() {
        return unitOfMeasurement_Enum;
    }

    public String getUnitOfMeasurement_String() {
        return unitOfMeasurement_String;
    }

    private void lookUpString(){
        switch (unitOfMeasurement_Enum){
            case NA:
                unitOfMeasurement_String = "N/A";
                break;
            case TEMPERATURE_DEGREESCELSIUS:
                unitOfMeasurement_String = "°C";
                break;
            case TEMPERATURE_DEGREESFAHRENHEIT:
                unitOfMeasurement_String = "°F";
                break;
            case VELOCITY_KILOMETERSPERHOUR:
                unitOfMeasurement_String = "km/h";
                break;
            case VELOCITY_MILESPERHOUR:
                unitOfMeasurement_String = "mph";
                break;
            case VOLUME_LITRESPERSQUAREMETER:
                unitOfMeasurement_String = "l/m²";
                break;
            case VOLUME_GALLONSPERSQUAREINCH:
                unitOfMeasurement_String = "gal/in²";
                break;
            case PRESSURE_BAR:
                unitOfMeasurement_String = "bar";
                break;
            case PRESSURE_POUNDFORCEPERSQUAREINCH:
                unitOfMeasurement_String = "pndf/in²";
                break;
            case DISTANCE_METERS:
                unitOfMeasurement_String = "m";
                break;
            case DISTANCE_FEET:
                unitOfMeasurement_String = "f";
                break;
            case RELATION_PERCENT:
                unitOfMeasurement_String = "%";
                break;
        }
    }
}
