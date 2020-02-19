package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Feeder;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;

import static frc.team5115.Constants.*;

public class PickupBallAuto extends CommandBase {

    Drivetrain drivetrain;
    Locationator locationator;
    Limelight limelight;
    Feeder feeder;
    boolean foundBall;


    public PickupBallAuto(Drivetrain drivetrain, Locationator locationator, Limelight limelight, Feeder feeder) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.limelight = limelight;
        this.feeder = feeder;
        foundBall = false;
    }
    /*
    Init Steps:
    1. Switch pipeline
     */

    @Override
    public void initialize() {
        limelight.setPipeline(Pipeline.Balls);
        drivetrain.stop();
    }
    /*on loop:
    Point at any ball in sight. After like 5 consecutive ticks of seeing a ball, move toward the ball.
     */
    double lastAngle;
    @Override
    public void execute() {
        limelight.setPipeline(Pipeline.Balls);
        System.out.println("working");
        double angle;
        if (limelight.hasTarget()) { // if we have a target
            System.out.println("Have target");
            angle = limelight.getXAngle() + locationator.getAngle();
            foundBall = true;
            lastAngle = angle;
        } else {

            if(foundBall) //if we have found that shit before, go there.
                drivetrain.angleHold(lastAngle, AUTO_MAX_THROTTLE);
            else
                drivetrain.stop();
            return;
        }

        //true means it rolls after it. It will stop if it stops. False means it just goes at a constant speed.
        double throttle = AUTO_MAX_THROTTLE;
        System.out.println("throttle = " + throttle);
        //throttle = 0;
        drivetrain.angleHold(angle, throttle);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;//feeder.isBall();
    } //todome make this from the intake sensor math.
}