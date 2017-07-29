package de.thm.smarthome.global.beans;

import de.thm.smarthome.global.enumeration.EMessageCode;

import java.io.Serializable;

/**
 * Created by Nils on 11.06.2017.
 */
public class MessageBean implements Serializable{
    private EMessageCode messageCode_Enum;
    private String message_String = "NULL";

    public MessageBean(EMessageCode messageCode_Enum) {
        this.messageCode_Enum = messageCode_Enum;
        lookUpString();
    }

    public MessageBean(boolean returnValue) {
        if(returnValue)
            messageCode_Enum = EMessageCode.SUCCESS;
        else
            messageCode_Enum = EMessageCode.FAIL;

        lookUpString();
    }

    public EMessageCode getMessageCode_Enum() {
        return messageCode_Enum;
    }

    public String getMessage_String() {
        return message_String;
    }

    private void lookUpString(){
        switch (messageCode_Enum){
            case NA:
                message_String = "N/A";
                break;
            /*case LOGINFAILED:
                message_String = "N/A";
                break;
            case LOGINSUCCESSFUL:
                message_String = "N/A";
                break;
            case LOGOUTFAILED:
                message_String = "N/A";
                break;
            case LOGOUTSUCCESSFUL:
                message_String = "N/A";
                break;
            case WRONGPASSWORD:
                message_String = "N/A";
                break;
            case WRONGUSERNAME:
                message_String = "N/A";
                break;
            case NOTLOGGEDIN:
                message_String = "Es existiert kein Benutzer mit diesem Benutzernamen oder der Benutzer ist nicht eingeloggt!";
                break;
            case HEATINGCOULDNOTBECREATED:
                message_String = "N/A";
                break;
            case SHUTTERCOULDNOTBECREATED:
                message_String = "N/A";
                break;
            case THERMOMETERCOULDNOTBECREATED:
                message_String = "N/A";
                break;
            case LOGGEDIN:
                message_String = "User ist eingeloggt.";
                break;
            case DBERROR:
                message_String = "N/A";
                break;
            case LOGGEDOUT:
                message_String = "N/A";
                break;
            case UNDOSUCCESSFUL:
                message_String = "N/A";
                break;
            case COMMANDINVOKEDSUCCESSFULLY:
                message_String = "N/A";
                break;
            case COMMANDINVOCATIONFAILED:
                message_String = "N/A";
                break;
            case SWITCHEDON:
                message_String = "N/A";
                break;
            case SWITCHEDOFF:
                message_String = "N/A";
                break;
            case ALREADYSWITCHEDON:
                message_String = "N/A";
                break;
            case ALREADYSWITCHEDOFF:
                message_String = "N/A";
                break;
            case TEMPERATUREADJUSTMENTSUCCESSFUL:
                message_String = "N/A";
                break;
            case TEMPERATUREADJUSTMENTFAILED:
                message_String = "N/A";
                break;
            case MOVEDUPSUCCESSFULLY:
                message_String = "N/A";
                break;
            case MOVEUPFAILED:
                message_String = "N/A";
                break;
            case MOVEDOWNFAILED:
                message_String = "N/A";
                break;
            case MOVEDDOWNSUCCESSFULLY:
                message_String = "N/A";
                break;
            case UNDOFAILED:
                message_String = "N/A";
                break;
            case MOVETOPOSITIONFAILED:
                message_String = "N/A";
                break;
            case MOVETOPOSITIONSUCCESSFUL:
                message_String = "N/A";
                break;
            case SWITCHONFAILED:
                message_String = "N/A";
                break;
            case SWITCHOFFFAILED:
                message_String = "N/A";
                break;
            case ALREADYMOVEDDOWN:
                message_String = "N/A";
                break;
            case ALREADYMOVEDUP:
                message_String = "N/A";
                break;
            case ALREADYATTHISPOSITION:
                message_String = "N/A";
                break;
            case USERCREATEDSUCCESSFULLY:
                message_String = "N/A";
                break;
            case USERCREATIONFAILED:
                message_String = "N/A";
                break;
            case USERDELETEDSUCCESSFULLY:
                message_String = "N/A";
                break;
            case USERDELETIONFAILED:
                message_String = "N/A";
                break;
            case USERALTEREDSUCCESSFULLY:
                message_String = "N/A";
                break;
            case USERALTERATIONFAILED:
                message_String = "N/A";
                break;
            case NOLOGGEDINUSER:
                message_String = "Es existiert kein eingeloggter Benutzer!";
                break;
            case USERNAMENOTEXIST:
            message_String = "Es existiert kein User mit diesem Benutzernamen!";
                break;
            case USERNOTEXISTNOTLOGGEDIN:
                message_String = "Es existiert kein Benutzer mit diesem Benutzernamen oder der Benutzer ist nicht eingeloggt!";
                break;*/
            case SUCCESS:
                message_String = "erfolgreich";
                break;
            case FAIL:
                message_String = "fehlgeschlagen";
                break;
            case LOGGEDIN:
                message_String = "eingeloggt";
                break;
            case NOTLOGGEDIN:
                message_String = "nicht eingeloggt";
                break;/*
            case WEATHERSTATIONCOULDNOTBECREATED:
                message_String = "N/A";
                break;*/

            /*case LoginFailed:
                return "Login fehlgeschlagen.";
            case LoginSuccessful:
                return "Login erfolgreich.";
            case LogoutFailed:
                return "Logout fehlgeschlagen.";
            case LogoutSuccessful:
                return "Logout erfolgreich.";
            case WrongPassword:
                return "Falsches Passwort.";
            case WrongUsername:
                return "Unbekannter Benutzername.";
            case NotLoggedIn:
                return "Benutzer nicht eingeloggt.";
            case HeatingCouldNotBeCreated:
                return "Heizung konnte nicht angelegt werden.";
            case ShutterCouldNotBeCreated:
                return "Rollladen konnte nicht angelegt werden.";
            case ThermometerCouldNotBeCreated:
                return "Thermometer konnte nicht angelegt werden.";
            case LoggedIn:
                return "Benutzer eingeloggt.";
            case DBError:
                return "Fehler in der Datenbank.";
            case LoggedOut:
                return "Benutzer ausgeloggt.";
            case UndoSuccessful:
                return "Undo erfolgreich.";
            case CommandInvokedSuccessfully:
                return "Command erfolgreich ausgeführt.";
            case CommandInvocationFailed:
                return "Command konnte nicht ausgeführt werden.";
            case SwitchedOn:
                return "Gerät eingeschaltet.";
            case SwitchedOff:
                return "Gerät ausgeschaltet.";
            case AlreadySwitchedOn:
                return "Gerät ist bereits eingeschaltet.";
            case AlreadySwitchedOff:
                return "Gerät ist bereits ausgeschaltet.";
            case TemperatureAdjustmentSuccessful:
                return "Temperatur erfolgreich angepasst.";
            case TemperatureAdjustmentFailed:
                return "Temperatur konnte nicht angepasst werden.";
            case MovedUpSuccessfully:
                return "Hochfahren erfolgreich.";
            case MoveUpFailed:
                return "Hochfahren fehlgeschlagen.";
            case MoveDownFailed:
                return "Herunterfahren fehlgeschlagen.";
            case MovedDownSuccessfully:
                return "Herunterfahren erfolgreich.";
            case UndoFailed:
                return "Undo konnte nicht ausgeführt werden.";
            case MoveToPositionFailed:
                return "Position konnte nicht geändert werden.";
            case MoveToPositionSuccessful:
                return "Position erfolgreich geändert.";
            case SwitchOnFailed:
                return "Gerät konnte nicht eingeschaltet werden.";
            case SwitchOffFailed:
                return "Gerät erfolgreich eingeschaltet.";
            case AlreadyMovedDown:
                return "Unterste Position bereits erreicht.";
            case AlreadyMovedUp:
                return "Oberste Position bereits erreicht.";
            case AlreadyAtThisPosition:
                return "Position bereits erreicht.";
            case UserCreatedSuccessfully:
                return "Benutzer erfolgreich angelegt.";
            case UserCreationFailed:
                return "Benutzer konnte nicht angelegt werden.";
            case UserDeletedSuccessfully:
                return "Benutzer erfolgreich gelöscht.";
            case UserDeletionFailed:
                return "Benutzer konnte nicht gelöscht werden.";
            case UserAlteredSuccessfully:
                return "Benutzer erfolgreich geändert.";
            case UserAlterationFailed:
                return "Benutzer konnte nicht geändert werden.";
            case WeatherStationCouldNotBeCreated:
                return "Wetterstation konnte nicht angelegt werden.";
            default:
                return "N/A";*/
        }
    }

}
