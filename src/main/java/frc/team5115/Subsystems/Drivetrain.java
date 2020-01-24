package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants;
import frc.team5115.Robot.RobotContainer;

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

    double I = 0;
    double lastAngle = 0;


    public Drivetrain(RobotContainer x) {
        //this.locationator = x.locationator;

        System.out.println("Locationator is null. Getting from main robot class.");
        locationator = x.locationator;

        frontLeft = new TalonSRX(Constants.FRONT_LEFT_MOTOR_ID);
        frontRight = new TalonSRX(Constants.FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(Constants.BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(Constants.BACK_RIGHT_MOTOR_ID);
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
        //called lots of times per seconds.;

            leftSpd = (x + y) * throttle;
            rightSpd = (x - y) * throttle;

        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, rightSpd);
    }

    public static double clamp(double d, double min, double max) {
        d = Math.min(max, d);
        return Math.max(min, d);
    }

    public static double clamp(double d, double val) {
        return clamp(d, -val, val);
    }

    @Override
    public void resetTargetAngle() { //set the current target angle to where we currently are.
        targetAngle = locationator.getAngle();
        I = 0;
        lastAngle = 0;
    }

    @Override
    public void angleHold(double targetAngle, double y) {
        this.targetAngle = targetAngle;
        double kP = 0.025;
        double kI = 0.0;
        double kD = 0.1;// Hey if you are implementing a d part, use the navx.getRate
        double currentAngle = locationator.getAngle();
        double P = kP * (currentAngle - targetAngle);
        if(P < 0.2 && P > -0.2) {
            I = I + (P * kI);
        }
        double D = -kD * (lastAngle - currentAngle); //finds the difference in the last tick.
        double output = P + I + D;
        output = clamp(output, 0.5);
        this.drive(-output, y, 1);
        lastAngle = currentAngle;
    }

    public void driveByWire(double x, double y) { //rotate by wire
        targetAngle += x * 2.5; //at 50 ticks a second, this is 50 degrees a second because the max x is 1.
        angleHold(targetAngle, y);
    }

    @Override
    public double getSpeedInchesPerSecond() {
        double rightSpd = frontRight.getSelectedSensorVelocity();
        double leftSpd = -backLeft.getSelectedSensorVelocity();

        return  ((rightSpd + leftSpd) / 2) * 30.7692 * Math.PI / 4090;
    }

}

