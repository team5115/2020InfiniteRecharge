package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import jdk.jfr.Experimental;

import static java.lang.Math.min;

//todome this
@Experimental
public class PickupBall extends CommandBase {

    Drivetrain drivetrain;
    Locationator locationator;
    Limelight limelight;


    PickupBall(Drivetrain drivetrain, Locationator locationator, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.limelight = limelight;
    }

    /*
    Init Steps:
    1. Switch pipeline
     */

    @Override
    public void initialize() {
        limelight.setPipeline(Constants.Pipeline.CustomGripPipeline);
    }
    /*on loop:
    Point at any ball in sight. After like 5 consecutive ticks of seeing a ball, move toward the ball.
     */

    @Override
    public void execute() {
        double angle;
        if (limelight.hasTarget()) { // if we don't have a target
            angle = limelight.getXAngle() + locationator.getAngle();
        } else {
            System.out.println("Can't find a ball.");
            return;
        }

        double throttle = Constants.SHOOTING_DISTANCE - limelight.calculateDistanceFromBase();
        throttle = min(throttle, Constants.MAX_AUTO_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
        drivetrain.angleHold(angle, throttle);
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) System.out.println("Error: Interrupted");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}