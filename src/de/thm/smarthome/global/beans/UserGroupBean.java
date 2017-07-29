package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EUserGroup;

public class UserGroupBean {
    private EUserGroup userGroup_Enum;
    private String userGroup_String = "NULL";

    public UserGroupBean(EUserGroup userGroup_Enum) {
        this.userGroup_Enum = userGroup_Enum;
        lookUpString();
    }

    public EUserGroup getUserGroup_Enum() {
        return userGroup_Enum;
    }

    public String getUserGroup_String() {
        return userGroup_String;
    }

    private void lookUpString(){
        switch (userGroup_Enum){
            case NA:
                userGroup_String = "N/A";
                break;
            case ADMINISTRATOR:
                userGroup_String = "Administrator";
                break;
            case ENDUSER:
                userGroup_String = "EndUser";
                break;

        }
    }
}
