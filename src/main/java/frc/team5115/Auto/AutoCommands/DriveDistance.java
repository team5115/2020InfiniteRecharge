package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Configuration.Constants;
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
        System.out.println("targetLocation = " + targetLocation);
        double angle = locationator.getCurrentLocation().angleFrom(targetLocation);
        double throttle = Drivetrain.clamp(locationator.getCurrentLocation().distanceFrom(targetLocation)/50 * Drivetrain.clamp(Math.abs((25/(angle - locationator.getAngle()))),1),
                Constants.AUTO_MAX_THROTTLE);
        System.out.println("Angle hold: " + angle + "  Throttle: " + throttle);
        drivetrain.angleHold(angle,throttle);
    }

    @Override
    public void end(boolean interrupted) {
        if(interrupted) System.out.println("Error: Interrupted");
        System.out.println("Done with Drive distance command");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return locationator.getCurrentLocation().distanceFrom(targetLocation) < 25;
    }
}
