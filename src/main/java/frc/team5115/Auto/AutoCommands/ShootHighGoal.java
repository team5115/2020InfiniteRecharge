package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;


public class ShootHighGoal extends SequentialCommandGroup {

    Drivetrain drivetrain;
    Locationator locationator;
    Shooter shooter;
    Limelight limelight;
    Timer timer;

    final Loc2D goalLocation = null;

    /*
    1. Aim at the thing using the limelight
    2. Shoot the balls while stopped.
     */

    public ShootHighGoal(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.shooter = shooter;
        this.limelight = limelight;
        System.out.println("constructed ShootHighGoal command");
        timer = new Timer();
        addCommands(new AimAndDistanceHighGoal());//, new Shooter.ShootForTime(shooter)); todome test and add shooter part
        //todome make it target dis
    }

    class AimAndDistanceHighGoal extends CommandBase {
        double throttle;

        @Override
        public void initialize() {
            System.out.println("Starting High Goal Aiming");
        }

        @Override
        public void execute() {
            limelight.setPipeline(2); //this ensures that we are looking at the right pipeline for the object.
            double angle = 127;
            if (limelight.hasTarget()) { // if we don't have a target
                angle = limelight.getXAngle() + locationator.getAngle();
                //System.out.println("angle = " + (angle - locationator.getAngle()));
            } /*else { //todome reimplemt crap below
                angle = locationator.
                        getCurrentLocation().
                        angleFrom(goalLocation);
            }*/
            //else return;

            throttle = -(Constants.SHOOTING_DISTANCE - limelight.calculateDistanceFromBase())/100;
            throttle = Drivetrain.clamp(throttle, Constants.MAX_AUTO_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
            //System.out.println("Distance from the base: " + limelight.calculateDistanceFromBase() + " throttle: " + throttle);
            //System.out.println("throttle = " + throttle);
            //throttle = 0; //todome eliminate to return forward backward handling.
            drivetrain.angleHold(angle, throttle);
        }

        @Override
        public void end(boolean interrupted) {
            if (interrupted) System.out.println("Error: Interrupted");
            System.out.println("we done with that linin up shit bro.");
            drivetrain.stop();
        }

        @Override
        public boolean isFinished() {
            return false;//abs(locationator.getAngle()) < 5 && throttle > 0;
        }
    }
}
