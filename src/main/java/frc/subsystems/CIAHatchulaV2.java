/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 - 2019 FIRST. All Rights Reserved.                      */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*                                                                            */
/* This project has been created by FIRST Robotics Team 291 CIA:              */
/* Creativity In Action located in Erie, Pennsylvania                         */
/* www.team291.com                                                            */
/*                                                                            */
/* Programmers for 2019 Build Season: Christopher Hess and Likhith Borela     */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

//Importing Libraries that will be used in the code
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CIAHatchulaV2{
    boolean isIn, isUp; //Used to tell is solenoids are in or out
    Solenoid solenoidPivot, solenoidEject; //Creates solenoid names
    boolean isPivotReversed, isEjectReversed; //Creates reversing
    int currentState;
    /*
    0 = Down
    1 = Up
    2 = Eject
    */

    public CIAHatchulaV2(int solenoidPivotPort, int solenoidEjectPort){
        solenoidPivot = new Solenoid(solenoidPivotPort); //Creates solenoid
        solenoidEject = new Solenoid(solenoidEjectPort); //Creates solenoid
        this.isPivotReversed = false; //Defaults to false
        this.isEjectReversed = false; //Defaults to false   
        this.currentState = 0;                                                                                         
    }

    public static enum HatchStateV2{ //Creates the different states
        EJECT, //Used to eject the Hatchula
        UP, //Used to have the hatchula up
        DOWN, //Used to have the hatchula down
        CURRENT //Used to stay the same
    }

    public void setHatchState (HatchStateV2 desiredState){ //Used to change the state to what is desired
        switch(desiredState){ //Uses a switch to use the wanted state to do what is desired
            case EJECT: //Used to eject hatchula
                this.currentState = 2;
                this.setPivotPiston(true);
                this.setEjectPiston(true);
                break;
            case UP: //Used to put hatchula up
                this.currentState = 1;
                this.setPivotPiston(true);
                this.setEjectPiston(false);
                break;
            case DOWN: //Used to keep the hatchula down
                this.currentState = 0;
                this.setPivotPiston(false);
                this.setEjectPiston(false);
                break;
            case CURRENT:
                if (currentState == 0){
                    this.setHatchState(HatchStateV2.DOWN);
                } else if (currentState == 1){
                    this.setHatchState(HatchStateV2.UP);
                } else {
                    this.setHatchState(HatchStateV2.EJECT);
                }
                break;
        }
    }

    private void setPivotPiston(boolean pistonState){ //Used to set the pivot piston in or out
        if(isPivotReversed){
            solenoidPivot.set(!pistonState);
        } else{
            solenoidPivot.set(pistonState);
        }
    }
    
    private void setEjectPiston(boolean pistonState){ //Used to set the eject piston
        if(isPivotReversed){
            solenoidEject.set(!pistonState);
        } else{
            solenoidEject.set(pistonState);
        }
    }
    
    public void setReversedValues(boolean newIsPivotReversed, boolean newIsEjectReversed){
        this.isPivotReversed = newIsPivotReversed;
        this.isEjectReversed = newIsEjectReversed;
    }
        
    public void update(){
        SmartDashboard.putBoolean("Eject In?", this.isIn);
        SmartDashboard.putBoolean("Pivot Up?", this.isUp);
    }
}
