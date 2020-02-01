package frc.team5115;

public class Constants{

    public static final byte FRONT_LEFT_MOTOR_ID = 3;
    public static final byte FRONT_RIGHT_MOTOR_ID = 4;
    public static final byte BACK_LEFT_MOTOR_ID = 1;
    public static final byte BACK_RIGHT_MOTOR_ID = 2;

    public static final byte INTAKE_MOTOR_ID = 5;
    public static final byte SHOOTER_MOTOR_ID = 6;

    public static final boolean KID_MODE = false;
    public static final double KID_MODE_MAX_SPEED = 0.4;
    public static final double NORMAL_MODE_MAX_SPEED = 0.6;
    public static final double MIN_XBOX_THROTTLE = 0.25;

    public static final StartingConfiguration startingConfiguration = StartingConfiguration.Right;
    public static final double startY = 0;
    public static final int startingAngle = 90; //90 is looking away from the driver stations.
    public static final double MAX_AUTO_THROTTLE = 0.4;
    public static final double CAMERA_HEIGHT = 10; //units: inches. todome update
    public static final double CAMERA_ANGLE = 15; //units: degrees. todome update
    public static final double SHOOTIN_DISTANCE = 120; //units: inches. todome update
    public static final double HIGH_GOAL_HEIGHT = 90; //units: inches. todome update
    public static final double BALL_TARGET_AREA = 5;
    public static final int LINE_TARGET_Y = 100;

    public static boolean USING_XBOX = true;

    //Joystick
    public static byte JOYSTICK_X_AXIS_ID = 0;
    public static byte JOYSTICK_Y_AXIS_ID = 1;
    public static byte JOYSTICK_THROTTLE_AXIS_ID = 3;

    //X-Box
    public static byte XBOX_Y_AXIS_ID = 1;
    public static byte XBOX_X_AXIS_ID = 4;
    public static byte XBOX_THROTTLE_1_ID = 3;
    public static byte XBOX_THROTTLE_2_ID = 2;


    public static final byte INTAKE_BUTTON_ID = 1;
    public static final byte SHOOTER_BUTTON_ID = 2;
    public static final byte AUTO_LINEUP_BUTTON_ID = 5;
    public static final byte AUTO_TURN_ASSIST_BUTTON_ID = 6;
    public static final byte RESET_BUTTON = 3;

    public enum StartingConfiguration {
        Right, Middle, Left;
        public double getX() {
            switch (this) {
                case Right:
                    return 50;
                case Middle:
                    return 100;
                case Left:
                    return 200;
            }
            return 0;
        }
    }

    public enum Pipeline {
        DriveCamera, CustomGripPipeline, GreenLedMode;
        public int getPipelineNumber() {
            switch(this) {
                case DriveCamera:
                    return 0;
                case CustomGripPipeline:
                    return 1;
                case GreenLedMode:
                    return 2;
            }
            return -1;
        }
    }
}

