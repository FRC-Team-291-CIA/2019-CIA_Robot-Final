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

package frc.util;

public class CIAPIDV2 {
    double P, I, D; //Proportional, Integral, Derivitive
    double error, previousError; //Errors need when calulating PID
    double dT = 0.02; //Duration of time (Should always be 0.02 for the code loops every 20ms)
    double calcP, calcI, calcD; //Used to calculate actual PID
    double calcPID; //Used to get total PID

    public CIAPIDV2(double newP, double newI, double newD){ //Constructor
        this.P = newP; //Takes in the inputs
        this.I = newI;
        this.D = newD;
    }

    public double calculatePID(double currentValue, double wantedValue){
        this.error = (wantedValue - currentValue); //Calculates error (Wanted - Current)
        this.previousError = this.error; //Saves the Error to be used in the next run
        this.calcP = (this.error*this.P); //Calculates portportional (Error * P Constant)
        this.calcI += (this.I*this.error*this.dT); //Calcualted integral (I Constant * Error * Time Duration)
        this.calcD = (this.D*((this.error - previousError)/this.dT));
        this.calcPID = (this.calcP + this.calcI + this.calcD); //Adds all of PID
        return this.calcPID; //Returns the caluclated PID
    }

    public void setPID(double newP, double newI, double newD){ //Sets new PID constants
        this.setP(newP);
        this.setI(newI);
        this.setD(newD);
    }

    public void setP(double newP){ //Sets P constants
        this.P = newP;
    }

    public void setI(double newI){ //Sets I constants
        this.I = newI;
    }

    public void setD(double newD){ //Sets D constants
        this.D = newD;
    }

    @Deprecated //Duration Time (DT) should always remain 0.02 under normal circumstances
    public void setDT(double newDT){ //Sets dT constants
        this.dT = newDT;
    }

    public double getP(){ //Returns P constant
        return this.P;
    }

    public double getI(){ //Returns I constant
        return this.I;
    }

    public double getD(){ //Returns D constant
        return this.D;
    }

    public double getDT(){ //Returns dT constant
        return this.dT;
    }

    public double getError(){ //Returns error
        return this.error;
    }

    public double getPreviousError(){ //Returns previous error
        return this.previousError;
    }
}