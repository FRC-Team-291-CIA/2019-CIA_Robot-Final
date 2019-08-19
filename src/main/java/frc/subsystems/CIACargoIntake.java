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

//importing libraries that will be used in the class
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

//starting class
public class CIACargoIntake{

    String intakeStateSmartDashboard = "Stationary";
    public Spark intakeMotor; //initializing the motor controller for controlling the output of the intake motor
    double motorPower;//the power of the intake motor

    public CIACargoIntake(int intakeMotorPort){
        //constructor for the object
        intakeMotor = new Spark(intakeMotorPort);
        intakeMotor.setInverted(true);
    }

    //used to show the state  of the arm
    public static enum IntakeState{
        INTAKING,
        DEPOSITING,
        STATIONARY
    }

    //sets the state of the arm
    public void setIntakeState(IntakeState desiredState){
        switch(desiredState){
            case INTAKING:
                //state is used when arm is trying to take a ball, basically the intake motor is at full power in a counterclockwise manner
                setIntakePower(Constants.intakeVoltage);
                intakeStateSmartDashboard = "Intaking";
                break;
            case DEPOSITING:
                //exact opposite of INTAKING
                setIntakePower(Constants.outtakeVoltage);
                intakeStateSmartDashboard = "Outtaking";
                break;
            case STATIONARY:
                //this is when the robot has the cargo acquired
                setIntakePower(Constants.stationaryVoltage);
                intakeStateSmartDashboard = "Stationary";
        }
    }

    //sets intake power
    public void setIntakePower(double power){
        motorPower = power;
        intakeMotor.set(motorPower);
    }

    //gives info to smartdashboard
    public void update(){
        SmartDashboard.putString("Intaking", intakeStateSmartDashboard);
        SmartDashboard.putNumber("Motor Voltage Value", motorPower);
    }
}     
