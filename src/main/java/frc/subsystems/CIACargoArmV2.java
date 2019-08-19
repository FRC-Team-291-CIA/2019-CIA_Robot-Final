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
import frc.sensors.CIAAnalogEncoder;
import frc.util.CIAPIDV2;

public class CIACargoArmV2 {
    Spark motorZero, motorOne; //Creates Motors
    CIAAnalogEncoder leftPOT, rightPOT; //Creates counters
    double leftAngle, leftVolt, rightAngle, rightVolt, motorPower; //Used for POTs
    boolean isLeftGood, isRightGood; //Booleans for connection
    SpeedControllerGroup motors; //Creates motor group 
    CIAPIDV2 pid; //Creates PID
    double armAngle; //Used for the current arm angle
    double angleGround, angleRocket, angleCargobay, angleFeeder, angleHold = 0; //Used for preset arm angles
    double currentP, currentI, currentD, permP, permPline, permI, permD, pidVal = 0; //Used in PID, to store current values
    //Above current means its what its at, Perm means thats the permant value used from constants file
    int currentState = 0; //Used for storing the current state of the arm. List is below
    /*
    0 = Nothing
    1 = Ground
    2 = Rocket
    3 = Feeder
    4 = Cargobay
    5 = Hold
    */
    int previousState = 0; //Used for storing the previous state of the arm. List is above

    //Normal constructor
    public CIACargoArmV2(int motorZeroPort, int motorOnePort, int leftPOTPort, int rightPOTPort){
        motorZero = new Spark(motorZeroPort); //Creates the motors
        motorOne = new Spark(motorOnePort);
        motors = new SpeedControllerGroup(motorZero, motorOne); //Creates the motor
        leftPOT = new CIAAnalogEncoder(leftPOTPort); // Creates the left POT
        leftPOT.setInverted(false); //Keeps it uninvtered. Impossible to install backwards
        rightPOT = new CIAAnalogEncoder(rightPOTPort); //Creates the right POT
        rightPOT.setInverted(true); //Keeps it invtered. Impossible to install backwards
        pid = new CIAPIDV2(0.00, 0.00, 0.00); //Creates the PID defaulting to all zeros
    }

    //Arm states are below
    public static enum ArmStateV2{
        CURRENT,
        NOTHING,
        GROUND,
        ROCKET,
        FEEDER, 
        CARGOBAY,
        HOLD
    }

    public void setArmState(ArmStateV2 wantedState){
        switch(wantedState){
            case NOTHING:
                this.setCurrentPID(0.00, 0.00, 0.00); //Sets the PID to zero, arm shouldn't move
                this.setMotors(0.00);
                currentState = 0;
                break;
            case GROUND:
                this.setCurrentPID(0.005, 0.00, 0.00); //Sets the P low, arm drops slowly
                this.setCalculatedMotors(pid.calculatePID(this.armAngle, this.angleGround));
                currentState = 1;
                break;
            case ROCKET:
                this.setCurrentPID(this.getPFromLine(this.angleRocket), this.permI, this.permD);
                this.setCalculatedMotors(pid.calculatePID(this.armAngle, this.angleRocket));
                currentState = 2;
                break;
            case FEEDER:
                this.setCurrentPID(this.getPFromLine(this.angleFeeder), this.permI, this.permD);
                this.setCalculatedMotors(pid.calculatePID(this.armAngle, this.angleFeeder));
                currentState = 3;
                break;
            case CARGOBAY:
                this.setCurrentPID(this.getPFromLine(this.angleCargobay), this.permI, this.permD);
                this.setCalculatedMotors(pid.calculatePID(this.armAngle, this.angleCargobay));
                currentState = 4;
                break;
            case HOLD:
                this.setCurrentPID(this.getPFromLine(this.angleHold), this.permI, this.permD);
                this.setCalculatedMotors(pid.calculatePID(this.armAngle, this.angleHold));
                currentState = 5;
                break;
            case CURRENT:
                if (currentState == 0){
                    this.setArmState(ArmStateV2.NOTHING);
                } else if (currentState == 1){
                    this.setArmState(ArmStateV2.GROUND);
                } else if (currentState == 2){
                    this.setArmState(ArmStateV2.ROCKET);
                } else if (currentState == 3){
                    this.setArmState(ArmStateV2.FEEDER);
                } else if (currentState == 4){
                    this.setArmState(ArmStateV2.CARGOBAY);
                } else {
                    this.setArmState(ArmStateV2.HOLD);
                }
                break;
        }
    }

    public void setAngles(double ground, double rocket, double cargobay, double feeder, double hold){
        this.angleGround = ground;
        this.angleRocket = rocket;
        this.angleCargobay = cargobay;
        this.angleFeeder = feeder;
        this.angleHold = hold;
    }

    public double getPFromLine(double possAngle){
        return (this.permPline/90)*possAngle;
    }

    public void setCalculatedMotors(double pidInput){
        if (armAngle == -999){
            motors.set(0.00);
        } else {
            motors.set(pidInput);
            this.motorPower = pidInput;
        }
    }

    public void setMotors(double input){ //Should not be used for PID
        motors.set(input);
    }

    public double getCurrentAngle(){
        //Below checks each POT for connections
        if(leftPOT.isConnected()){
            isLeftGood = true;
        } else {
            isLeftGood = false;
        }
		if(rightPOT.isConnected()) {
            isRightGood = true;
        } else { 
            isRightGood = false; 
        }

        //Left is prioritized over right when reading angle
        if (isLeftGood) {
            armAngle = this.leftAngle;
        } else if (isRightGood) {
            armAngle = this.rightAngle;
        } else { 
            armAngle = -999;
        }

        return this.armAngle;
    }

    public void setZeros(double newLeftZero, double newRightZero){
        this.setLeftZero(newLeftZero);
        this.setRightZero(newRightZero);
    }

    private void setLeftZero(double newZero){
        leftPOT.setWorkingZero(newZero);
    }

    private void setRightZero(double newZero){
        rightPOT.setWorkingZero(newZero);
    }

    public void setUnpluggedVoltages(double newLeftVolt, double newRightVolt){
        this.setLeftUnpluggedVolt(newLeftVolt);
        this.setRightUnpluggedVolt(newRightVolt);
    }

    private void setLeftUnpluggedVolt(double newVolt){
        leftPOT.setUnpluggedVoltage(newVolt);
    }

    private void setRightUnpluggedVolt(double newVolt){
        rightPOT.setUnpluggedVoltage(newVolt);
    }

    //Used outside the class to get values for PID
    public void setPermPID(double p, double i, double d, double pLine){
        this.permP = p;
        this.permI = i;
        this.permD = d;
        this.permPline = pLine;
    }

    //Used in the class to set the PID
    private void setCurrentPID(double newP, double newI, double newD){
        this.currentP = newP;
        pid.setP(this.currentP);
        this.currentI = newI;
        pid.setI(this.currentI);
        this.currentD = newD;
        pid.setD(this.currentD);
    }

    //Used outsides the class in robot periodic to constantly get values and send data to the user
    public void update(){
        this.leftAngle = leftPOT.getAngle();
        this.rightAngle = rightPOT.getAngle();
        this.leftVolt = leftPOT.getRawVoltage();
        this.rightVolt = rightPOT.getRawVoltage();
        this.armAngle = this.rightAngle; //this.getCurrentAngle(); //Overridden due to senor errors
        SmartDashboard.putNumber("P", this.currentP);
        SmartDashboard.putNumber("I", this.currentI);
        SmartDashboard.putNumber("D", this.currentD);
        SmartDashboard.putNumber("Error", pid.getError());
        SmartDashboard.putNumber("Left Angle", this.leftAngle);
        SmartDashboard.putNumber("Right Angle", this.rightAngle);
        SmartDashboard.putNumber("Left Volt", this.leftVolt);
        SmartDashboard.putNumber("Right Volt", this.rightVolt);
        SmartDashboard.putNumber("Arm Power V2", this.motorPower);
        SmartDashboard.putNumber("Arm State", this.currentState);
    }
}