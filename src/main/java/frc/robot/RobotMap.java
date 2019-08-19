package frc.robot;

public class RobotMap{
    //Joysticks and buttons below. Driver must be in X mode.
        public static final int driverPort = 0; //Driver joystick port for controlling
            public static final int driverYAxis = 1; //Driver left stick Y Axis
            public static final int driverXAxis = 4; //Driver right stick X Axis
            public static final int highSpeedButton = 6; //Driver higher speed button | Rb Button
            public static final int hatchulaOuttakeButton = 1; //Enable Climb Button | Green "A"
            public static final int cameraSwitchButtonDriver = 4; //Switch Camera | Yellow "Y"
            public static final int cargoOutakeButton = 5; //Take out switch | Lb Button
  
        //Operator is in D mode
        public static final int operatorPort = 1; // Operator joystick port for controlling
            public static final int hatchPivotDownButton = 11; //Operator button for hatchula down | Left Joystick Button 
            public static final int hatchPivotUpButton = 12; //Operator button for resting | Right Joystick Button
            public static final int armDoNothingButton = 9; //Operator button for it to Do Nothing | "Back" Button 
            public static final int armGroundButton = 2; //Operator button for setting the arm to ground level | Green "A" 
            public static final int armRocketLevelButton = 1; //Operator button for setting the arm to rocket level | Blue "X"
            public static final int armCargoshipButton = 3; //Operator button for setting the arm to cargoship level | Red "B"
            public static final int armFeederButton = 4; //Used to move arm to feeder | Yellow "Y" 
            public static final int armHoldButton = 10; //Operator button for holding | "Start" Button
            public static final int armOperatorButton = 7; //Switch Camera | Lt Button
            public static final int armIntakeButton = 8; //Operator button for intaking | Rt Button
            public static final int climbMechButton = 6; //Enable Climb Button | Rb Button
            public static final int armFudgeAxis = 1; //Used to move arm and down | Left Joystick
            public static final int operatorClimbAxis = 3; //Used to climb | Right Joystick
            public static final int cameraSwitchButtonOperator = 5; //Switch Camera | Lb Button

    //Motor ports below
        //Below is the drive base
        public static final int rightDriveSidePorts = 1;
        public static final int leftDriveSidePorts = 0;
        //Below are for the arm
        public static final int cargoArmAPort = 4;
        public static final int cargoArmBPort = 5;
        public static final int cargoIntakePort = 6;
        //Below are for the climber
        public static final int climbPortsLeftSide = 2;
        public static final int climbPortsRightSide = 3;

    //Solenoid Ports below
        public static final int solenoidPivotPort = 2;
        public static final int solenoidEjectPort = 0;

    //Sensor Ports
        //Analong Inputs
        public static final int cargoArmLeftEncoderPort = 0;
        public static final int cargoArmRightEncoderPort = 1;
}