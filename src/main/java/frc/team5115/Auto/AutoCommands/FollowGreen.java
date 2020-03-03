package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

import static frc.team5115.Configuration.Constants.*;


public class FollowGreen extends SequentialCommandGroup {

    Drivetrain drivetrain;
    Locationator locationator;
    Shooter shooter;
    Limelight limelight;
    Timer timer;

    final Loc2D goalLocation = new Loc2D(StartingConfiguration.Middle.getX(), 0);

    /*
    1. Aim at the thing using the limelight
    2. Shoot the balls while stopped.
     */

    public FollowGreen(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.shooter = shooter;
        this.limelight = limelight;
        limelight.setPipeline(Pipeline.GreenLedMode);
        System.out.println("constructed ShootHighGoal command");
        timer = new Timer();
        addCommands(new AimAndDistanceHighGoal());//, new Shooter.ShootForTime(shooter)); follow green has no shooter part.
    }

    class AimAndDistanceHighGoal extends CommandBase {
        double throttle;

        @Override
        public void initialize() {
            System.out.println("Starting Follow Green");
        }

        @Override
        public void execute() {
            double angle = 0;
            if (limelight.hasTarget()) { // if we don't have a target
                angle = limelight.getXAngle() + locationator.getAngle();
                //System.out.println("angle = " + (angle - locationator.getAngle()));
            } else { //This is a simple follow green: no need for anything extra other than stop if it cannot find it
                drivetrain.stop();
                return;
            }

            throttle = -(AUTO_SHOOTIN_DISTANCE - limelight.calculateDistanceFromBase())/100;
            throttle = Drivetrain.clamp(throttle, AUTO_MAX_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
            //System.out.println("Distance from the base: " + limelight.calculateDistanceFromBase() + " throttle: " + throttle);
            throttle = 0; //eliminate to return forward backward handling.
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
