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

package frc.robot;

//Import Subsystems files
import frc.subsystems.CIADriveBaseV2;
import frc.subsystems.CIAHatchulaV2;
import frc.subsystems.CIAHatchulaV2.HatchStateV2;
import frc.subsystems.CIACargoArmV2;
import frc.subsystems.CIACargoArmV2.ArmStateV2;
import frc.subsystems.CIACargoIntake;
import frc.subsystems.CIACargoIntake.IntakeState;
import frc.subsystems.CIAClimber;
import frc.subsystems.CIAClimber.ClimbState;

//Import the camera
import frc.sensors.CIALimeLight;

//Below are general imports
import edu.wpi.first.wpilibj.TimedRobot; //Imports Timed Robot
import edu.wpi.first.wpilibj.Joystick; //Imports the Joystick

public class Robot extends TimedRobot {
  //Below is Drive Base
  CIADriveBaseV2 CIADriveV2 = new CIADriveBaseV2(RobotMap.leftDriveSidePorts, RobotMap.rightDriveSidePorts);

  //Below is the hatchula
  CIAHatchulaV2 CIAHatchV2 = new CIAHatchulaV2(RobotMap.solenoidPivotPort, RobotMap.solenoidEjectPort);

  //Below is the cargo arm
  CIACargoArmV2 CIAArmV2 = new CIACargoArmV2(RobotMap.cargoArmAPort, RobotMap.cargoArmBPort, RobotMap.cargoArmLeftEncoderPort, RobotMap.cargoArmRightEncoderPort);

  //Below is the intake 
  CIACargoIntake CIAIntake = new CIACargoIntake(RobotMap.cargoIntakePort);

  //Below is the climber
  CIAClimber CIAClimb = new CIAClimber(RobotMap.climbPortsLeftSide, RobotMap.climbPortsRightSide, Constants.climbRightInvert, Constants.climbAllInvert);
  
  //Below is the camera
  CIALimeLight CIALime = new CIALimeLight();

  //Below creates HID
  Joystick driver = new Joystick(RobotMap.driverPort);
  Joystick operator = new Joystick(RobotMap.operatorPort);

  @Override
  //Ran once the robot starts up to set values
  public void robotInit() {
    //All constants come from the Constants.java file
    //Constants for drive base
    CIADriveV2.setDeadband(Constants.driveDeadband);
    CIADriveV2.setSpeedLimits(Constants.driveLow, Constants.driveHigh);
    CIADriveV2.setReverses(Constants.driveRightInvert, Constants.driveAllInvert, Constants.driveStickInvert);

    //Constants for cargo arm
    CIAArmV2.setUnpluggedVoltages(Constants.leftUnpluggedVoltage, Constants.rightUnpluggedVoltage);
    CIAArmV2.setPermPID(Constants.armPValue, Constants.armIValue, Constants.armDValue, Constants.armPLineValue);
    CIAArmV2.setZeros(Constants.leftZero, Constants.rightZero);
    CIAArmV2.setArmState(ArmStateV2.NOTHING);
    CIAArmV2.setAngles(Constants.armGroundLevel, Constants.armRocketLevel, Constants.armBayLevel, Constants.armFeederLevel, Constants.armHoldLevel);

    //Constants for climber
    CIAClimb.setMaxPower(Constants.climbMaxPower);

    //Constants for hatchula
    CIAHatchV2.setReversedValues(Constants.hatchPivotIsReversed, Constants.hatchEjectIsReversed);
  }

  @Override
  //Below code is ran all the time, this usually updates values for the robot
  public void robotPeriodic() {
    CIADriveV2.update();
    CIAHatchV2.update();
    CIALime.update();
    CIAArmV2.update();
    CIAClimb.update();

    //Below is for camera switching
    if (driver.getRawButtonPressed(RobotMap.cameraSwitchButtonDriver) || operator.getRawButtonPressed(RobotMap.cameraSwitchButtonOperator)){

      CIALime.switchCameras();
    
    }
  }

  @Override
  //Runs once when the robot enters disabled state
  public void disabledInit() {
    System.out.println("Robot has been disabled");
  }

  @Override
  //Runs peridoically when the robot is in disabled state
  public void disabledPeriodic() {
    
  }

  @Override
  //Runs once when the robot enters auto state
  public void autonomousInit() {
    System.out.println("Autonomous Mode Activated");
    //Below sets values and starting states of objects
    CIADriveV2.setLowLimit(Constants.driveAutoLow);
    CIAArmV2.setArmState(ArmStateV2.HOLD);
    CIAHatchV2.setHatchState(HatchStateV2.DOWN);
  }

  @Override
  //Runs periodically when the robot is in auto state
  public void autonomousPeriodic() {
    this.teleopPeriodic(); //Uses the teleop code
  }

  @Override
  //Runs once when the robot enters teleop state
  public void teleopInit() {
    System.out.println("Teleop Mode Activated");
    CIAArmV2.setArmState(ArmStateV2.CURRENT); //Carries over the current arm state defaults to hold
    CIADriveV2.setSpeedLimits(Constants.driveLow, Constants.driveHigh); //Sets the teleop constants for driving
  }

  @Override
  //Runs periodically when the robot is in teleop state
  public void teleopPeriodic() {
    //Below is driving code
    CIADriveV2.arcadeDrive(driver.getRawAxis(RobotMap.driverYAxis), driver.getRawAxis(RobotMap.driverXAxis), driver.getRawButton(RobotMap.highSpeedButton));

    //Below is hatchula code being correlated with buttons
    if (driver.getRawButton(RobotMap.hatchulaOuttakeButton)){

      CIAHatchV2.setHatchState(HatchStateV2.EJECT);

    } else if (operator.getRawButton(RobotMap.hatchPivotUpButton)){

      CIAHatchV2.setHatchState(HatchStateV2.UP);

    } else if (operator.getRawButton(RobotMap.hatchPivotDownButton)){

      CIAHatchV2.setHatchState(HatchStateV2.DOWN);

    } else {

      CIAHatchV2.setHatchState(HatchStateV2.CURRENT);

    }
    
    //Below is the climber code
    if (operator.getRawButton(RobotMap.climbMechButton)){

      CIAClimb.setClimbState(ClimbState.MOVING, operator.getRawAxis(RobotMap.operatorClimbAxis));

    } else {

      CIAClimb.setClimbState(ClimbState.STATIOANRY, 0.00);

    }
    
    //Below is cargo intake code being correlated with buttons
    if(operator.getRawButton(RobotMap.armIntakeButton)){

      CIAIntake.setIntakeState(IntakeState.INTAKING);

    } else if (driver.getRawButton(RobotMap.cargoOutakeButton)){

      CIAIntake.setIntakeState(IntakeState.DEPOSITING);

    } else {

      CIAIntake.setIntakeState(IntakeState.STATIONARY);

    }

    //Below is for the ARM
    if (operator.getRawButton(RobotMap.armDoNothingButton)){

      CIAArmV2.setArmState(ArmStateV2.NOTHING);

    }else if(operator.getRawButton(RobotMap.armGroundButton)){
  
      CIAArmV2.setArmState(ArmStateV2.GROUND);
    
    }else if (operator.getRawButton(RobotMap.armRocketLevelButton)){
      
      CIAArmV2.setArmState(ArmStateV2.ROCKET);

    }else if (operator.getRawButton(RobotMap.armCargoshipButton)){
      
      CIAArmV2.setArmState(ArmStateV2.CARGOBAY);

    } else if (operator.getRawButton(RobotMap.armFeederButton)){

      CIAArmV2.setArmState(ArmStateV2.FEEDER);
        
    } else if (operator.getRawButton(RobotMap.armHoldButton)){

      CIAArmV2.setArmState(ArmStateV2.HOLD);

    } else {

      CIAArmV2.setArmState(ArmStateV2.CURRENT);

    }  
  }

  @Override
  //Runs once when the robot enters test state
  public void testInit() {
    System.out.println("Test Mode Activated");

  }

  @Override
  //Runs peridocailly when the robot is in teleop state
  public void testPeriodic() {
  
  } 
}