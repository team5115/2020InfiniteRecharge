package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import frc.team5115.autotools.Loc2D;

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

        timer = new Timer();
        addCommands(new Aim(),
                new Shoot()
        );
    }

    class Aim extends CommandBase {
        @Override
        public void initialize() {
            System.out.println("Starting High Goal");
        }

        @Override
        public void execute() {
            limelight.setPipeline(1); //this ensures that we are looking at the right pipeline for the object.
            double angle;
            if (limelight.hasTarget()) { // if we don't have a target
                angle = limelight.getXAngle() + locationator.getAngle();
            } else {
                angle = locationator.
                        getCurrentLocation().
                        angleFrom(goalLocation);
            }

            double throttle = min(throttle, RobotContainer.maxForwardSpeed); //max speed 0.5. Also add a minimum speed of 0.1.
            drivetrain.angleHold(angle, throttle);
        }
    }

        @Override
        public boolean isFinished() {
            return abs(locationator.getAngle()) < 5;
    }
}
