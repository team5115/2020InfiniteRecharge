package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Locationator;

public class DriveDistance extends CommandBase {

    Loc2D targetLocation;
    Drivetrain drivetrain;
    Locationator locationator;

    public DriveDistance(Loc2D targetLocation, Drivetrain drivetrain, Locationator locationator) {
        if (targetLocation == null) {
            System.out.println("Error: target location is null.");
            return;
        }
        this.targetLocation = targetLocation;
        this.drivetrain = drivetrain;
        this.locationator = locationator;
    }

    @Override
    public void initialize() {
        System.out.println("Starting Drive Distance Command");

    }

    @Override
    public void execute() {
        double angle = locationator.getCurrentLocation().angleFrom(targetLocation);
        double throttle = Math.min(locationator.getCurrentLocation().distanceFrom(targetLocation), 200);
        drivetrain.angleHold(angle,throttle);
    }

    @Override
    public void end(boolean interrupted) {
        if(interrupted) System.out.println("Error: Interrupted");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        if (targetLocation == null) {
            return true;
        }
        return locationator.getCurrentLocation().distanceFrom(targetLocation) < 5;
    }
}
