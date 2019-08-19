package frc.robot;

public class Constants {
        //Below are drive base constants
        public static final double driveDeadband = 0.02; //Deadband
        public static final double driveAutoLow = 0.60; //Auto Power
        public static final double driveLow = 0.6; //Teleop Lower Power
        public static final double driveHigh = 0.9; //Teleop Higher Power
        public static final boolean driveAllInvert = false; //Invert For All, used when mechanical team screws up a motor
        public static final boolean driveRightInvert = true; //Invert For Right, used when mechanical team screws up a motor
        public static final boolean driveStickInvert = false; //Invert For the Driver Stick, used when mechanical team screws up a motor

        //Below are climbing constants
        public static final boolean climbRightInvert = false; //Invert For the Right, used when mechanical team screws up a motor
        public static final boolean climbAllInvert = false; //Invert For All, used when mechanical team screws up a motor
        public static final double climbMaxPower = 0.75; //Max power to climb motors
  
        //Below are cargo arm constants
        public static final double leftUnpluggedVoltage = 0.2245; //Used for being able to tell when the left POT is disconnected
        public static final double rightUnpluggedVoltage = 0.2258; //Used for being able to tell when the right POT is disconnected
        public static final double leftZero = 311.93; //Used for zeroing the left POT
        public static final double rightZero = 118.80; //Used for zeroing the right POT
        public static final double intakeVoltage = 0.75; //Used for the voltage for the intake motor
        public static final double outtakeVoltage = -intakeVoltage; //Used for the voltage for the intake when outtaking motor
        public static final double stationaryVoltage = 0.00; //Used when its not supposed to move

        //Below is for the hatchula
        public static final boolean hatchPivotIsReversed = false; //Used if the hatch pivot mechanically gets reversed
        public static final boolean hatchEjectIsReversed = false; //Used if the hatch eject mechanically gets reversed

        //Below are arm PID values
        public static double armPValue = 0.02; //Proportional of the arm
        public static double armIValue = 0.00; //Intgeral of the arm
        public static double armDValue = 0.00; //Derivitive of the arm
        public static double armPLineValue = 0.055; //Used in the PID from the line, equation below
        //ArmPLine = armPLineVaalue divided by 90 (total angle movement) multiplied by the current angle

        //Below are arm angles | Power
        public static final double armGroundLevel = 0.00; //Actual Bottom
        public static final double armRocketLevel = 40.000; //40.000 without Ball
        public static final double armFeederLevel = 60.000; //60.000 without Ball
        public static final double armBayLevel = 60.000; //60.000 without Ball
        public static final double armHoldLevel = 70; //70 without Ball
}