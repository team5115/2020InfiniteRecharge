package frc.team5115;
import io.github.oblarg.oblog.annotations.Log;
import io.github.oblarg.oblog.Loggable;

public class Constants implements Loggable{

    public static final int FRONT_LEFT_MOTOR_ID = 1;

    public static final int FRONT_RIGHT_MOTOR_ID = 3;

    public static final int BACK_LEFT_MOTOR_ID = 4;

    public static final int BACK_RIGHT_MOTOR_ID = 2;

    public static final int INTAKE_MOTOR_ID = 5;

    public static final int SHOOTER_MOTOR_ID = 6;

    public static final int CLIMBER_MOTOR_ID = 7;

    public static final int FEEDER_MOTOR_ID = 8;

    public static final int ACCERLERATOR_MOTOR_ID = 9;

    public static final int INTAKE_BUTTON_ID = 14;

    public static final int SHOOTER_BUTTON_ID = 15;

    public static final int CLIMB_UP_BUTTON_ID = 16;

    public static final int ClIMB_DOWN_BUTTON_ID = 17;

    public static final int kSlotIdx = 0;
    @Log
    public static final int kPIDLoopIdx = 0;

    public static final int kTimeoutMs = 30;

    @Log
    public static final Gains kGains_Velocit = new Gains( 0.25, 0.001, 20, 1023.0/7200.0,  300,  1.00);





}

