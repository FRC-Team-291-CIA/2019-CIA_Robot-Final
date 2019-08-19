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
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CIADriveBaseV2{
    //Below the motors are created
    Spark motorZero, motorOne, motorTwo, motorThree, motorFour, motorFive, motorSix, motorSeven;
    SpeedControllerGroup leftSide, rightSide; //Used as motor groupds
    double mathLeft, mathRight; //Used for doing math
    double speedLow = 0.5; //Low speed limiter
    double speedHigh = 0.75; //High speed limiter
    double deadband = 0.02; //Deadband
    boolean isHighSpeed = false; //Used for checking high speed
    boolean isRightRevsered = false; //Used for revsered motor
    boolean isAllReversed = false; //Used for reversed motors
    boolean isAracdeStickSwitched = false; //Used for reversed the stick

    //Below is the contructor for two motors
    public CIADriveBaseV2(int motorZeroPort, int motorOnePort){
        //Below creates motors
        motorZero = new Spark(motorZeroPort);
        motorOne = new Spark(motorOnePort);
        //Below creates motor groups
        leftSide = new SpeedControllerGroup(motorZero);
        rightSide = new SpeedControllerGroup(motorOne);
    }
    
    //Below is the contructor for four motors
    public CIADriveBaseV2(int motorZeroPort, int motorOnePort, int motorTwoPort, int motorThreePort){
        //Below creates motors
        motorZero = new Spark(motorZeroPort);
        motorOne = new Spark(motorOnePort);
        motorTwo = new Spark(motorTwoPort);
        motorThree = new Spark(motorThreePort);
        //Below creates motor groups
        leftSide = new SpeedControllerGroup(motorZero, motorOne);
        rightSide = new SpeedControllerGroup(motorTwo, motorThree);
    }
    
    //Below is the contructor for six motors
    public CIADriveBaseV2(int motorZeroPort, int motorOnePort, int motorTwoPort, int motorThreePort, int motorFourPort, int motorFivePort){
        //Below creates motors
        motorZero = new Spark(motorZeroPort);
        motorOne = new Spark(motorOnePort);
        motorTwo = new Spark(motorTwoPort);
        motorThree = new Spark(motorThreePort);
        motorFour = new Spark(motorFourPort);
        motorFive = new Spark(motorFivePort);
        //Below creates motor groups
        leftSide = new SpeedControllerGroup(motorZero, motorOne, motorTwo);
        rightSide = new SpeedControllerGroup(motorThree, motorFour, motorFive);
    }
    
    //Below is the contructor for eight motors
    public CIADriveBaseV2(int motorZeroPort, int motorOnePort, int motorTwoPort, int motorThreePort, int motorFourPort, int motorFivePort, int motorSixPort, int motorSevenPort){
        //Below creates motors
        motorZero = new Spark(motorZeroPort);
        motorOne = new Spark(motorOnePort);
        motorTwo = new Spark(motorTwoPort);
        motorThree = new Spark(motorThreePort);
        motorFour = new Spark(motorFourPort);
        motorFive = new Spark(motorFivePort);
        motorSix = new Spark(motorSixPort);
        motorSeven = new Spark(motorSevenPort);
        //Below creates motor groups
        leftSide = new SpeedControllerGroup(motorZero, motorOne, motorTwo, motorThree);
        rightSide = new SpeedControllerGroup(motorFour, motorFive, motorSix, motorSeven);
    }

    public void setReverses(boolean newRightReverse, boolean newAllReverse, boolean newStickReverse){
        this.isRightRevsered = newRightReverse;
        this.isAllReversed = newAllReverse;
        this.isAracdeStickSwitched = newStickReverse;
    }

    public void setSpeedLimits(double newLowSpeed, double newHighSpeed){
        this.setLowLimit(newLowSpeed);
        this.setHighLimit(newHighSpeed);
    }

    public void setLowLimit(double newLowSpeed){
        this.speedLow = newLowSpeed;
    }

    public void setHighLimit(double newHighSpeed){
        this.speedHigh = newHighSpeed;
    }

    public void setDeadband(double newDeadband){
        this.deadband = newDeadband;
    }

    public void arcadeDrive(double inputYAxis, double inputXAxis, boolean isHighSpeed){
        if (this.isAracdeStickSwitched){
            this.mathLeft = inputYAxis - inputXAxis;
            this.mathRight = inputYAxis + inputXAxis;
        } else {
            this.mathLeft = inputYAxis + inputXAxis;
            this.mathRight = inputYAxis - inputXAxis;
        }
        
        this.isHighSpeed = isHighSpeed;
        if (isRightRevsered){
            this.mathRight = -this.mathRight;
        }
        
        if (isAllReversed){
            this.mathLeft = -this.mathLeft;
            this.mathRight = -this.mathRight;
        }

        if (this.isHighSpeed){
            this.mathLeft = this.mathLeft*speedHigh;
            this.mathRight = this.mathRight*speedHigh;
        } else {
            this.mathLeft = this.mathLeft*speedLow;
            this.mathRight = this.mathRight*speedLow;
        }

        leftSide.set(this.mathLeft);
        rightSide.set(this.mathRight);
    }

    public void tankDrive(double inputLeft, double inputRight, boolean isHighSpeed){
        this.mathLeft = inputLeft;
        this.mathRight = inputRight;
        this.isHighSpeed = isHighSpeed;

        if (isRightRevsered){
            this.mathRight = -this.mathRight;
        }
        
        if (isAllReversed){
            this.mathLeft = -this.mathLeft;
            this.mathRight = -this.mathRight;
        }

        if (this.isHighSpeed){
            this.mathLeft = this.mathLeft*speedHigh;
            this.mathRight = this.mathRight*speedHigh;
        } else {
            this.mathLeft = this.mathLeft*speedLow;
            this.mathRight = this.mathRight*speedLow;
        }

        this.setLeftRightSpeed(this.mathLeft, this.mathRight);
    }

    private void setLeftRightSpeed(double leftSpeed, double rightSpeed){
        this.leftSide.set(this.checkDeadband(leftSpeed));
        this.rightSide.set(this.checkDeadband(rightSpeed));
    }

    private double checkDeadband(double input){
        if (input <= this.deadband){
            return 0.00;
        } else {
            return input;
        }
    }
    
    public void update(){
        SmartDashboard.putNumber("Left Speed", this.mathLeft);
        SmartDashboard.putNumber("Right Speed", this.mathRight);
        SmartDashboard.putBoolean("Is High Speed", this.isHighSpeed);
    }   
}