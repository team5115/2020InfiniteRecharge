package frc.team5115;

public class Constants{
    //speed stuff
    public static final boolean KID_MODE = false;
    public static final double KID_MODE_MAX_SPEED = 0.4;
    public static final double NORMAL_MODE_MAX_SPEED = 1;
    public static final double MIN_XBOX_THROTTLE = 0.25;

    //autonomous stuff
    public static final StartingConfiguration startingConfiguration = StartingConfiguration.Right;
    public static final double startY = 120;
    public static final int startingAngle = -45; //90 is looking away from the driver stations.

    public static final double AUTO_MAX_THROTTLE = 0.75;
    public static final double AUTO_CAMERA_HEIGHT = 10; //units: inches.
    public static final double AUTO_CAMERA_ANGLE = 15; //units: degrees.
    public static final double AUTO_SHOOTIN_DISTANCE = 120; //units: inches. todome update
    public static final double AUTO_HIGH_GOAL_HEIGHT = 90; //units: inches.
    //motor ids
    public static final byte FRONT_LEFT_MOTOR_ID = 3;
    public static final byte FRONT_RIGHT_MOTOR_ID = 4;
    public static final byte BACK_LEFT_MOTOR_ID = 1;
    public static final byte BACK_RIGHT_MOTOR_ID = 2;

    public static final byte INTAKE_MOTOR_ID = 5;
    public static final byte SHOOTER_MOTOR_ID = 8;
    public static final byte SCISSOR_MOTOR_ID = 9;
    public static final byte WINCH_MOTOR_ID = 10;
    public static final byte FEEDER_MOTOR_ID = 6;
    public static final byte ACCELERATOR_MOTOR_ID = 7;

    //button ids
    public static final byte INTAKE_RUN_BUTTON_ID = 1;
    public static final byte INTAKE_REVERSE_BUTTON_ID = 2;
    public static final byte FEEDER_DEJAM_BUTTON_ID = 9;
    public static final byte SHOOTER_BUTTON_ID = 6;
    public static final byte CLIMB_UP_BUTTON_ID = 4;
    public static final byte ClIMB_DOWN_BUTTON_ID = 3;

    public static final byte AUTO_LINEUP_BUTTON_ID = 5;
    public static final byte AUTO_TURN_ASSIST_BUTTON_ID = 8;
    public static final byte AUTO_GET_BALL_BUTTON = 7; //wrong

    //Speed constants for subsystems.
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public static final Gains kGains_Velocity = new Gains( 0.25, 0.001, 20, 1023.0/7200.0,  300,  1.00);
    public static final double FEEDER_STORE_SPEED = -0.4;
    public static final double FEEDER_FLUSH_SPEED = -0.8;
    public static final double INTAKE_INHALE_SPEED = -0.3;

    //controller stuff.
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
    public static float XBOX_X_DEADZONE = 0.075f;

    //information on where we start
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
        DriveCamera, Balls, GreenLedMode;
        public int getPipelineNumber() {
            switch(this) {
                case DriveCamera:
                    return 0;
                case Balls:
                    return 3;
                case GreenLedMode:
                    return 2;
            }
            return -1;
        }
    }
}

