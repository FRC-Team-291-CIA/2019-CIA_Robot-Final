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

//Importing Librareis that will be used in the code
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 

public class CIAClimber {
    double climbMaxPower = 0.00; //Max Power
    double climbMathLeft = 0.00; //Varaible used for math
    double climbMathRight = 0.00; //Varaible used for math
    boolean motorActive = false; //Used for sending to the dahsboard if the arm is active
    SpeedControllerGroup leftSide, rightSide;
    boolean invertRight = false; //Booleans for inverting
    boolean invertAll = false; //Booleans for inverting
    String smartdashBoard = "NO_CLIMBING"; //Used for smartdash board

    public CIAClimber(int leftSidePort, int rightSidePort, boolean newInvertRight, boolean newInvertAll){ //Used for two motors
        Spark climbLeftOne = new Spark(leftSidePort); //Creates the first motor
        Spark climbRightOne = new Spark(rightSidePort); //Creates the second motor
        invertRight = newInvertRight; //Sets the invert
        invertAll = newInvertAll; //Sets the invert
        leftSide = new SpeedControllerGroup(climbLeftOne); //Used for grouping
        rightSide = new SpeedControllerGroup(climbRightOne); //Used for grouping
    }

    public CIAClimber(int leftSideOne, int leftSideTwo, int rightSideOne, int rightSideTwo, boolean newInvertRight, boolean newInvertAll){ //Used for two motors
        Spark climbLeftOne = new Spark(leftSideOne); //Creates the first motor
        Spark climbLeftTwo = new Spark(leftSideTwo); //Creates the first motor
        Spark climbRightOne = new Spark(rightSideOne); //Creates the second motor
        Spark climbRightTwo = new Spark(rightSideTwo); //Creates the second motor
        invertRight = newInvertRight; //Sets the invert
        invertAll = newInvertAll; //Sets the invert
        leftSide = new SpeedControllerGroup(climbLeftOne, climbLeftTwo); //Used for grouping
        rightSide = new SpeedControllerGroup(climbRightOne, climbRightTwo); //Used for grouping
    }

    public static enum ClimbState {
        STATIOANRY, //For no action
        MOVING //Climbs
    }

    public void setClimbState(ClimbState wantedState, double axisSpeed){
        switch(wantedState){
            case STATIOANRY:
                smartdashBoard = "NO_CLIMBING"; //Sets for smartdashboard
                leftSide.set(0.00); //Sets the motors to zero
                rightSide.set(0.00);
                motorActive = false; //Used for dashboard
                break;
            case MOVING:
                smartdashBoard = "CLIMB"; //Sets for smartdashboard
                climbMathLeft = climbMaxPower*axisSpeed; //Calculates the power
                climbMathRight = climbMathLeft;

                if (invertRight == true) { //Sets the invert for right
                    climbMathRight = -climbMathRight;
                }
                else{}

                if (invertAll == true){ //Sets the invert for left
                    climbMathRight = -climbMathRight;
                    climbMathLeft = -climbMathLeft;
                }
                else{}

                leftSide.set(climbMathLeft); //Sets the power
                rightSide.set(-climbMathRight); //Sets the power
                motorActive = true; //Used for dashboard
                break;
        }
    }

    public void setMaxPower(double newSpeed) { //Sets the max power
        climbMaxPower = newSpeed;
    }

    public double getMaxClimb() { //Retrives the max power
        return climbMaxPower;
    }

    public void update(){ //Used for dashabord
        SmartDashboard.putString("Climb State", this.smartdashBoard);
        SmartDashboard.putNumber("Climb Power", this.climbMathLeft);
    }
}