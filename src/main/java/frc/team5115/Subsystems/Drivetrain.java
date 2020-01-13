package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Auto.DriveBase;
import frc.team5115.Robot.RobotContainer;

public class Drivetrain extends SubsystemBase implements DriveBase {
    private final Locationator locationator;
    //instances of the speed controllers
    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;

    private double targetAngle; //during regular operation, the drive train keeps control of the drive. This is the angle that it targets.

    private double rightSpd;
    private double leftSpd;

    public Drivetrain(RobotContainer x) {
        this.locationator = x.locationator;

        frontLeft = new TalonSRX(1);
        frontRight = new TalonSRX(2);
        backLeft = new TalonSRX(3);
        backRight = new TalonSRX(4);

        frontLeft.set(ControlMode.Follower, backLeft.getDeviceID());
        frontRight.set(ControlMode.Follower, backRight.getDeviceID());

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    @Override
    public void stop() {
        drive(0,0,0);
    }

    @Override
    public void drive(double y, double x, double throttle) { //Change the drive output
        //called lots of times per seconds.
        y *= -1;

        //todome test this.
        //Math.sqrt(3.4* Math.log(x + y + 1));

        leftSpd = Math.min((x + y) * throttle, 1);
        rightSpd = Math.min((x - y) * throttle, 1);

        leftSpd = Math.max(leftSpd, -1);
        rightSpd = Math.max(rightSpd, -1);

//        System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
//        System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);

        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, rightSpd);
    }

    @Override
    public void resetTargetAngle() { //set the current target angle to where we currently are.
        targetAngle = locationator.getAngle();
        System.out.println("RESET RBW: Target Angle: " + targetAngle + " Current Angle: " + locationator.getAngle());
    }

    @Override
    public void angleHold(double targetAngle, double y) {
        this.targetAngle = targetAngle;
        double kP = 0.02;
        //double kD = 0.01; Hey if you are implementing a d part, use the navx.getRate
        double currentAngle = locationator.getAngle();
        double P = kP*(targetAngle - currentAngle);
        //double D = kD*((currentAngle - lastAngle)/0.02); //finds the difference in the last tick.
        P = Math.max(-0.5, Math.min(0.5, P));
        this.drive(y,P,1);
    }

    public void driveByWire(double x, double y) { //rotate by wire
        targetAngle += x*2.5; //at 50 ticks a second, this is 50 degrees a second because the max x is 1.
        angleHold(targetAngle, y);
    }

    @Override
    public double getSpeedInchesPerSecond() {
        double rightSpd = frontRight.getSelectedSensorVelocity();
        double leftSpd = -backLeft.getSelectedSensorVelocity();

        final double wheelSpd = ((rightSpd + leftSpd) / 2) * 30.7692 * Math.PI / 4090;
        System.out.println("Wheel Speeds = " + wheelSpd);
        return wheelSpd;
    }

}

