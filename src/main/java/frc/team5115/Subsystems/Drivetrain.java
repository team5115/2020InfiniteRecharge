package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Auto.DriveBase;
import frc.team5115.Robot.Robot;
import frc.team5115.Robot.RobotContainer;
import io.github.oblarg.oblog.Loggable;

import static frc.team5115.Configuration.Constants.*;

public class Drivetrain extends SubsystemBase implements DriveBase, Loggable {
    private Locationator locationator;

    private DriverStation driverStation = DriverStation.getInstance();

    //instances of the speed controllers
    private VictorSPX frontLeft;
    private VictorSPX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;

    Servo servo;

    private double targetAngle; //during regular operation, the drive train keeps control of the drive. This is the angle that it targets.

    private double rightSpd;
    private double leftSpd;

    boolean climbing;

    double throttle = .7;

    private RobotContainer robotContainer;

    public Drivetrain(RobotContainer x) {
        //this.locationator = x.locationator;
        robotContainer = x;

        servo = new Servo(1);
        locationator = x.locationator;

        frontLeft = new VictorSPX(FRONT_LEFT_MOTOR_ID);
        frontRight = new VictorSPX(FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(BACK_RIGHT_MOTOR_ID);

        //backRight.setInverted(true);

        //back motors are master

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        lastAngle = locationator.getAngle();
    }

    @Override
    public void stop() {
        drive(0, 0, 0);
    }

    @Override
    public void drive(double x, double y, double throttle) { //Change the drive output
        //called lots of times per seconds.
        //System.out.println("Driving with X:" + x + " Y: " + y + " throttle: " + throttle);
        //Math.sqrt(3.4* Math.log(x + y + 1));
        //todome
        //System.out.println("x = " + x);
        //System.out.println("y = " + y);
        leftSpd = (x-y) * throttle * (climbing ? GRANNY_MODE_SPEED : 1);
        rightSpd = (x+y) * throttle * (climbing ? GRANNY_MODE_SPEED : 1);
        //System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
        //System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);

        frontLeft.set(ControlMode.PercentOutput, leftSpd);
        frontRight.set(ControlMode.PercentOutput, rightSpd);
        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, rightSpd);

        //System.out.println("servo.get() = " + servo.get());
    }

    public double throttle(double increase, double decrease) {
        throttle += 0.03 *(increase - decrease);

        if (throttle > 1){
            throttle = 1;
        } else if(throttle < 0){
            throttle = 0;
        }
        return throttle;
    }

    public void setAngle() {
        if (!climbing) {
            double angleMath = DRIVING_CAM_MIN_ANGLE + (DRIVING_CAM_ANGLE_GAIN * getSpeed());
            servo.setAngle(angleMath > DRIVING_CAM_MAX_ANGLE ? DRIVING_CAM_MAX_ANGLE :
                           angleMath < DRIVING_CAM_MIN_ANGLE ? DRIVING_CAM_MIN_ANGLE : angleMath);
        }
        else {
            setAngleClimbing();
        }
    }

    public void setAngleClimbing() {
        servo.setAngle(POINTING_UP);
    }

    public void XBoxDrive(Joystick joy) {
        double x = joy.getRawAxis(XBOX_X_AXIS_ID);
        double y = joy.getRawAxis(XBOX_Y_AXIS_ID);

        double throttle1 = joy.getRawAxis(XBOX_THROTTLE_1_ID);
        double throttle2 = joy.getRawAxis(XBOX_THROTTLE_2_ID);

        //throttle is between 0.5 and 1
        double throttle = (1 - throttle1) + (1 - throttle2);
        throttle /= 2;
        //throttle is between 0 (dont move) and 1, (full move)
        throttle = ((1 - MIN_XBOX_THROTTLE) * throttle) + MIN_XBOX_THROTTLE;
        //new Throttle is now max 1 and min 0.2
        //System.out.println("throttle = " + throttle);
        //drive(x,y,throttle);

        driveByWire(x, y, throttle);
    }

    public static double clamp(double d, double min, double max) {
        d = Math.min(max, d);
        return Math.max(min, d);
    }

    public static double clamp(double d, double max) {
        return clamp(d, -max, max);
    }

    @Override
    public void resetTargetAngle() { //set the current txarget angle to where we currently are.
        targetAngle = locationator.getAngle();
        System.out.println("RESET RBW: Target Angle: " + targetAngle + " Current Angle: " + locationator.getAngle());
    }

    double I = 0;
    double lastAngle;

    public void relativeAngleHold(double targetAngle, double y) {
        this.targetAngle = targetAngle;
        double kI = 0.0;
        double kD = 0.1;
        double currentAngle = 0;
//        System.out.println("currentAngle = " + currentAngle);
//        System.out.println("lastAngle = " + lastAngle);
        double P = kP * (currentAngle - targetAngle);
        if (P < 0.2 && P > -0.2) {
            I = I + (P * kI);
        }
        double D = -kD * (lastAngle - currentAngle); //finds the difference in the last tick.
        double output = P + I + D;
        output = clamp(output, 0.5);
//        System.out.println("P = " + P);
//        System.out.println("I = " + I);
//        System.out.println("D = " + D);
//        System.out.println("output = " + output);
        this.drive(-output, y, 1);
        lastAngle = currentAngle;
    }
    double kP = 0.075;
    public void angleHold(double targetAngle, double y, double throttle) {
        this.targetAngle = targetAngle;
        double kI = 0.0;
        double kD = .1;// Hey if you are implementing a d part, use the navx.getRate
        double currentAngle = locationator.getAngle();
//        System.out.println("currentAngle = " + currentAngle);
//        System.out.println("lastAngle = " + lastAngle);
        double P = kP * (currentAngle - targetAngle);
        if (P < 0.2 && P > -0.2) {
            I = I + (P * kI);
        }
        //P *= (1.5-Math.abs(y));

        double D = -kD * (lastAngle - currentAngle); //finds the difference in the last tick.
        double output = P + I + D;
//        System.out.println("P = " + P);
//        System.out.println("I = " + I);
//        System.out.println("D = " + D);
//        System.out.println("output = " + output);
        this.drive(-output, y, throttle);
        lastAngle = currentAngle;
    }

    public void angleHold(double targetAngle, double y) {
        this.angleHold(targetAngle, y, .7);
    }

    public void driveByWire(double x, double y, double throttle) { //rotate by wire
        if (Math.abs(x) < XBOX_X_DEADZONE) x = 0;
        targetAngle += x; //at 50 ticks a second, this is 50 degrees a second because the max x is 1.

        if (Math.abs(targetAngle - locationator.getAngle()) > 30) {
            targetAngle = locationator.getAngle();
        }
        angleHold(targetAngle, y, throttle);
    }

    @Override
    public double getSpeedInchesPerSecond() {
        return 0;
    }

    public void printAllEncoders() {
        System.out.println("frontLeft: " + frontLeft.getSelectedSensorPosition());
        System.out.println("frontRight: " + frontRight.getSelectedSensorPosition());
        System.out.println("backLeft: " + backLeft.getSelectedSensorPosition());
        System.out.println("backRight: " + backRight.getSelectedSensorPosition());
    }


    public double getSpeed() {
        //easily debug sensors: System.out.println("fr:" + frontRight.getSelectedSensorVelocity() + "  fl:" + frontLeft.getSelectedSensorVelocity() + "  br:" + backLeft.getSelectedSensorVelocity() + "  bl:" + backLeft.getSelectedSensorVelocity());
        double rightSpd = frontRight.getSelectedSensorVelocity();
        double leftSpd = -frontLeft.getSelectedSensorVelocity();

        //System.out.println("Wheel Speeds = " + wheelSpd);
        return ((rightSpd + leftSpd) / 2) * 30.7692 * Math.PI / 4090;
    }

    public void isClimbing() {
        climbing = true;
    }

    public void debug() {
        System.out.println("getSpeed() = " + 15*getSpeed());
    }

}


