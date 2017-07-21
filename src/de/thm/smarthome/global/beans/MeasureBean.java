package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EUnitOfMeasurement;

/**
 * Created by Nils on 11.06.2017.
 */
public class MeasureBean {
    private double measure_Double;
    private UnitOfMeasurementBean unitOfMeasurement_Bean;

    public MeasureBean(double measure_Double, EUnitOfMeasurement unitOfMeasurement_Enum) {
        this.measure_Double = measure_Double;
        unitOfMeasurement_Bean = new UnitOfMeasurementBean(unitOfMeasurement_Enum);
    }

    public double getMeasure_Double() {
        return measure_Double;
    }

    public UnitOfMeasurementBean getUnitOfMeasurement_Bean() {
        return unitOfMeasurement_Bean;
    }

    public EUnitOfMeasurement getUnitOfMeasurement_Enum(){
        return unitOfMeasurement_Bean.getUnitOfMeasurement_Enum();
    }

    public String getUnitOfMeasurement_String(){
        return unitOfMeasurement_Bean.getUnitOfMeasurement_String();
    }
}
