package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj.MedianFilter;
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
    MedianFilter filter = new MedianFilter(20);


    public PickupBall(Drivetrain drivetrain, Locationator locationator, Limelight limelight) {
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
            drivetrain.stop();
            return;
        }
        if(limelight.getYAngle()>-12) {
            System.out.println("Found a target, but it is bs.");
            drivetrain.stop();
            return;
        }

        double throttle = Constants.MAX_AUTO_THROTTLE/2;
        double cal = filter.calculate(limelight.getArea());
        throttle = (Constants.BALL_TARGET_AREA - cal) / 20;
        System.out.println(limelight.getArea());
        System.out.println(cal);
        System.out.println("throttle = " + throttle);
        throttle = min(throttle, Constants.MAX_AUTO_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
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
    }
}