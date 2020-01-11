package frc.team5115;

import frc.team5115.Auto.StartingConfiguration;

public class Constants{

    public static final int FRONT_LEFT_MOTOR_ID = 1;
    public static final int FRONT_RIGHT_MOTOR_ID = 3;
    public static final int BACK_LEFT_MOTOR_ID = 4;
    public static final int BACK_RIGHT_MOTOR_ID = 2;

    public static final int INTAKE_MOTOR_ID = 5;
    public static final int SHOOTER_MOTOR_ID = 6;
    public static final int INTAKE_BUTTON_ID = 1;
    public static final int SHOOTER_BUTTON_ID = 2;

    public static final int LINE_TARGET_Y = 100;

    public static final StartingConfiguration startingConfiguration = StartingConfiguration.Left;
    public static final double startY = 20;
    public static final int startingAngle = 90; //90 is looking away from the driver stations.

    public static final double MAX_AUTO_THROTTLE = 0.5;
    public static final double CAMERA_HEIGHT = 10; //units: inches. todome update
    public static final double CAMERA_ANGLE = 10; //units: degrees. todome update
    public static final double SHOOTING_DISTANCE = 120; //units: inches. todome update
    public static final double HIGH_GOAL_HEIGHT = 98.25; //units: inches.

    public static final int X_AXIS_ID = 0;
    public static final int Y_AXIS_ID = 1;
    public static final int THROTTLE_AXIS_ID = 2;
}

