package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EDeviceManufacturer;
import de.thm.smarthome.global.enumeration.EModelVariant;

/**
 * Created by Nils on 11.06.2017.
 */
public class ModelVariantBean {
    private EModelVariant modelVariant_Enum;
    private String modelVariant_String = "NULL";

    public ModelVariantBean(EModelVariant modelVariant_Enum) {
        this.modelVariant_Enum = modelVariant_Enum;
        lookUpString();
    }

    public ModelVariantBean(EDeviceManufacturer buderus, String modelVariant) {
        //TODO: Based on manufacturer and modelVariant, assign ModelVariant
    }

    public EModelVariant getModelVariant_Enum() {
        return modelVariant_Enum;
    }

    public String getModelVariant_String() {
        return modelVariant_String;
    }

    private void lookUpString(){
        switch (modelVariant_Enum){
            case NA:
                modelVariant_String = "N/A";
                break;
        }
    }
}
