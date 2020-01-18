package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Auto.DriveBase;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.*;

public class Drivetrain extends SubsystemBase implements DriveBase {
    private Locationator locationator;
    //instances of the speed controllers
    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;

    private double targetAngle; //during regular operation, the drive train keeps control of the drive. This is the angle that it targets.

    private double rightSpd;
    private double leftSpd;

    double lastAngle = 0;

    public Drivetrain(RobotContainer x) {
        //this.locationator = x.locationator;

        System.out.println("Locationator is null. Getting from main robot class.");
        locationator = x.locationator;

        frontLeft = new TalonSRX(FRONT_LEFT_MOTOR_ID);
        frontRight = new TalonSRX(FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(BACK_RIGHT_MOTOR_ID);

        frontLeft.set(ControlMode.Follower, backLeft.getDeviceID());
        frontRight.set(ControlMode.Follower, backRight.getDeviceID());

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
        //todome test this.
        //Math.sqrt(3.4* Math.log(x + y + 1));

        leftSpd = (x + y) * throttle;
        rightSpd = (x - y) * throttle;

//        System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
//        System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);

        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, rightSpd);
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
    @Override
    public void angleHold(double targetAngle, double y) {
        System.out.println("lastAngle = " + lastAngle);
        this.targetAngle = targetAngle;
        double kP = 0.02;
        double kI = 0;
        double kD = 0;// Hey if you are implementing a d part, use the navx.getRate
        double currentAngle = locationator.getAngle();
        System.out.println("currentAngle = " + currentAngle);
        double P = kP * (targetAngle - currentAngle);
        I = I + P * kI;
        double D = kD * (currentAngle - lastAngle); //finds the difference in the last tick.
        double output = P + I + D;
        output = clamp(output, 0.5);
        System.out.println("P = " + P);
        System.out.println("I = " + I);
        System.out.println("D = " + D);
        System.out.println("output = " + output);
        this.drive(output, y, 1);
        lastAngle = currentAngle;
        System.out.println("lastAngle = " + lastAngle);
    }

    public void driveByWire(double x, double y) { //rotate by wire
        targetAngle += x * 2.5; //at 50 ticks a second, this is 50 degrees a second because the max x is 1.
        angleHold(targetAngle, y);
    }

    @Override
    public double getSpeedInchesPerSecond() {
        //easily debug sensors: System.out.println("fr:" + frontRight.getSelectedSensorVelocity() + "  fl:" + frontLeft.getSelectedSensorVelocity() + "  br:" + backLeft.getSelectedSensorVelocity() + "  bl:" + backLeft.getSelectedSensorVelocity());
        double rightSpd = frontRight.getSelectedSensorVelocity();
        double leftSpd = -backLeft.getSelectedSensorVelocity();

        final double wheelSpd = ((rightSpd + leftSpd) / 2) * 30.7692 * Math.PI / 4090;
        //System.out.println("Wheel Speeds = " + wheelSpd);
        return wheelSpd;
    }

}

