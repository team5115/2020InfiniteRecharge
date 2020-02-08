package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Auto.DriveBase;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.*;

public class Drivetrain extends SubsystemBase implements DriveBase {
    private Locationator locationator;
    //instances of the speed controllers
    private VictorSPX frontLeft;
    private VictorSPX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;

    private double targetAngle; //during regular operation, the drive train keeps control of the drive. This is the angle that it targets.

    private double rightSpd;
    private double leftSpd;


    public Drivetrain(RobotContainer x) {
        //this.locationator = x.locationator;
        locationator = x.locationator;

        frontLeft = new VictorSPX(FRONT_LEFT_MOTOR_ID);
        frontRight = new VictorSPX(FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(BACK_RIGHT_MOTOR_ID);

        backRight.setInverted(true);

        frontLeft.set(ControlMode.Follower, backLeft.getDeviceID());
        frontRight.set(ControlMode.Follower, backRight.getDeviceID());

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

            leftSpd = (x + y) * throttle;
            rightSpd = (x - y) * throttle;

//        System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
//        System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);

        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, -rightSpd);
    }

    public void XBoxDrive(Joystick joy) {
        double x = joy.getRawAxis(XBOX_X_AXIS_ID);
        double y = -joy.getRawAxis(XBOX_Y_AXIS_ID);
        System.out.println("y = " + y);
        double throttle1 = joy.getRawAxis(XBOX_THROTTLE_1_ID);
        double throttle2 = joy.getRawAxis(XBOX_THROTTLE_2_ID);

        //throttle is between 0.5 and 1
        double throttle = (1-throttle1) + (1-throttle2);
        throttle /= 2;
        //throttle is between 0 (dont move) and 1, (full move)
        throttle = ((1-MIN_XBOX_THROTTLE) * throttle) + MIN_XBOX_THROTTLE;
        //new Throttle is now max 1 and min 0.2
        //System.out.println("throttle = " + throttle);
        x*=0.5;
        y*=0.4;
        //drive(x,y,throttle);

        System.out.println("Remove me if working!");
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
    public void resetTargetAngle() { //set the current target angle to where we currently are.
        targetAngle = locationator.getAngle();
        System.out.println("RESET RBW: Target Angle: " + targetAngle + " Current Angle: " + locationator.getAngle());
    }

    double I = 0;
    double lastAngle = 0;

    public void relativeAngleHold(double targetAngle, double y) {
        this.targetAngle = targetAngle;
        double kP = 0.025;
        double kI = 0.0;
        double kD = 0.1;
        double currentAngle = 0;
//        System.out.println("currentAngle = " + currentAngle);
//        System.out.println("lastAngle = " + lastAngle);
        double P = kP * (currentAngle - targetAngle);
        if(P < 0.2 && P > -0.2) {
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


    public void angleHold(double targetAngle, double y, double throttle) {
        this.targetAngle = targetAngle;
        double kP = 0.025;
        double kI = 0.0;
        double kD = 0.1;// Hey if you are implementing a d part, use the navx.getRate
        double currentAngle = locationator.getAngle();
//        System.out.println("currentAngle = " + currentAngle);
//        System.out.println("lastAngle = " + lastAngle);
        double P = kP * (currentAngle - targetAngle);
        if(P < 0.2 && P > -0.2) {
            I = I + (P * kI);
        }
        double D = -kD * (lastAngle - currentAngle); //finds the difference in the last tick.
        double output = P + I + D;
        output = clamp(output, 0.5);
//        System.out.println("P = " + P);
//        System.out.println("I = " + I);
//        System.out.println("D = " + D);
//        System.out.println("output = " + output);
        this.drive(-output, y, throttle);
        lastAngle = currentAngle;
    }

    public void angleHold(double targetAngle, double y) {
        this.angleHold(targetAngle, y, 1);
    }

    public void driveByWire(double x, double y, double throttle) { //rotate by wire
        System.out.println("x = " + x);
        System.out.println("throttle = " + throttle);
        targetAngle += x * 2.5; //at 50 ticks a second, this is 50 degrees a second because the max x is 1.
        if(Math.abs(targetAngle - locationator.getAngle()) > 30) {
            targetAngle = locationator.getAngle();
        }
        angleHold(targetAngle, y, throttle);
    }

    public void printAllEncoders() {
        System.out.println("frontLeft: " + frontLeft.getSelectedSensorPosition());
        System.out.println("frontRight: " + frontRight.getSelectedSensorPosition());
        System.out.println("backLeft: " + backLeft.getSelectedSensorPosition());
        System.out.println("backRight: " + backRight.getSelectedSensorPosition());

    }

    @Override
    public double getSpeedInchesPerSecond() {
        //easily debug sensors: System.out.println("fr:" + frontRight.getSelectedSensorVelocity() + "  fl:" + frontLeft.getSelectedSensorVelocity() + "  br:" + backLeft.getSelectedSensorVelocity() + "  bl:" + backLeft.getSelectedSensorVelocity());
        double rightSpd = frontRight.getSelectedSensorVelocity();
        double leftSpd = -backLeft.getSelectedSensorVelocity();

        //System.out.println("Wheel Speeds = " + wheelSpd);
        return ((rightSpd + leftSpd) / 2) * 30.7692 * Math.PI / 4090;
    }

}

