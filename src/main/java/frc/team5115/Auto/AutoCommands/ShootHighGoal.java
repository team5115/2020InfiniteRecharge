package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

import static frc.team5115.Configuration.Constants.*;


public class ShootHighGoal extends SequentialCommandGroup {

    Drivetrain drivetrain;
    Locationator locationator;
    Shooter shooter;
    Limelight limelight;

    final Loc2D goalLocation = new Loc2D(StartingConfiguration.Middle.getX(), 0);

    /*
    1. Aim at the thing using the limelight
    2. Shoot the balls while stopped.
     */

    public ShootHighGoal(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;
        this.shooter = shooter;
        this.limelight = limelight;
        addRequirements(drivetrain, shooter);

        addCommands(new AimAndDistanceHighGoal());//, new Shooter.ShootTillEmpty(shooter)); todome when shooter is ready --> test.
    }

    class AimAndDistanceHighGoal extends CommandBase {
        double throttle;

        @Override
        public void initialize() {
            System.out.println("Starting High Goal Aiming");
            limelight.setPipeline(Pipeline.GreenLedMode);
        }

        @Override
        public void execute() {
            double angle;
            if (limelight.hasTarget() && limelight.getYAngle() > 0) { // if we don't have a target
                angle = limelight.getXAngle() + locationator.getAngle();

                throttle = -(AUTO_SHOOTIN_DISTANCE - limelight.calculateDistanceFromBase())
                        / 50;
                throttle = Drivetrain.clamp(throttle, AUTO_MAX_THROTTLE); //max speed 0.5. Also add a minimum speed of 0.1.
                System.out.println("Distance from the base: " + limelight.calculateDistanceFromBase() + " throttle: " + throttle);
                System.out.println("angle = " + (angle - locationator.getAngle()));
            } else {
                drivetrain.stop();
                return;
            }
            drivetrain.angleHold(angle, /*OLIVIA! PUT NEGATIVE HERE*/throttle);
            if(throttle < 0.2f) shooter.shoot();
        }

        @Override
        public void end(boolean interrupted) {
            if (interrupted) System.out.println("Error: Interrupted");
            drivetrain.stop();
            limelight.setPipeline(Pipeline.DriveCamera);
        }

        @Override
        public boolean isFinished() {
            return false;//abs(locationator.getAngle()) < 5 && throttle > 0;
        }
    }
}