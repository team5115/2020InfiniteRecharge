package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;

import static frc.team5115.Constants.MAX_AUTO_THROTTLE;

public class PickupBallAuto extends CommandBase {

    Drivetrain drivetrain;
    Locationator locationator;
    Limelight limelight;
    private final Joystick joystick;
    boolean foundBall;

    public PickupBallAuto(Drivetrain drivetrain, Locationator locationator, Limelight limelight, Joystick joystick) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.limelight = limelight;
        this.joystick = joystick;
        foundBall = false;
    }

    public PickupBallAuto(Drivetrain drivetrain, Locationator locationator, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.limelight = limelight;
        joystick = null;
        foundBall = false;
    }
    /*
    Init Steps:
    1. Switch pipeline
     */

    @Override
    public void initialize() {
        limelight.setPipeline(Constants.Pipeline.CustomGripPipeline);
        drivetrain.stop();
    }
    /*on loop:
    Point at any ball in sight. After like 5 consecutive ticks of seeing a ball, move toward the ball.
     */
    double lastAngle;
    @Override
    public void execute() {
        double angle;
        if (limelight.hasTarget() && limelight.getYAngle() + Constants.CAMERA_ANGLE < 0) { // if we have a target
            angle = limelight.getXAngle() + locationator.getAngle();
            foundBall = true;
            lastAngle = angle;
        } else {
            System.out.println("Can't find a ball.");
            if(foundBall) //if we have found that shit before, go there.
                drivetrain.angleHold(lastAngle, MAX_AUTO_THROTTLE);
            else
                drivetrain.stop();
            return;
        }
        boolean targetForDistance = false;
        //true means it rolls after it. It will stop if it stops. False means it just goes at a constant speed.
        double throttle;
        if(targetForDistance) {
            double distanceFromBall = limelight.calculateDistanceFromBall();
            throttle = -(30 - limelight.calculateDistanceFromBase())
                    / 30;
        }else {
            throttle = MAX_AUTO_THROTTLE;
        }
        System.out.println("throttle = " + throttle);
        throttle = Drivetrain.clamp(throttle, MAX_AUTO_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
        //throttle = 0;
        drivetrain.angleHold(angle, throttle);
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) System.out.println("Error: Interrupted");
        System.out.println("Done!");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    } //todome make this from the intake sensor math.
}