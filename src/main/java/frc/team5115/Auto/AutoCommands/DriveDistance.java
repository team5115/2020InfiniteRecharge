package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Configuration.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Locationator;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import static frc.team5115.Configuration.Constants.startingConfiguration;

public class DriveDistance extends CommandBase implements Loggable {

    Loc2D targetLocation;
    Drivetrain drivetrain;
    Locationator locationator;

    double throttle;
    double prevValue;

    public DriveDistance(Loc2D targetLocation, Drivetrain drivetrain, Locationator locationator) {
        if (targetLocation == null) {
            System.out.println("Error: target location is null.");
            return;
        }
        this.targetLocation = targetLocation;
        this.drivetrain = drivetrain;
        this.locationator = locationator;
    }

    @Log
    public double getDistance(){return locationator.getCurrentLocation().distanceFrom(targetLocation);}

    public void initialize() {
        System.out.println("Starting Drive Distance Command");
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        System.out.println("targetLocation = " + targetLocation);
        System.out.println("locationator.getCurrentLocation().getY() = " + locationator.getCurrentLocation().getY());
        double angle = locationator.getCurrentLocation().angleFrom(targetLocation);
        //  throttle = Drivetrain.clamp(locationator.getCurrentLocation().distanceFrom(targetLocation)/50 * Drivetrain.clamp(Math.abs((25/(angle - locationator.getAngle()))),1),Constants.AUTO_MAX_THROTTLE);
        throttle = (((Math.max(drivetrain.getEncoder(), prevValue)) - 5000) / 5000);
        System.out.println("Angle hold: " + angle + "  Throttle: " + throttle);
        drivetrain.angleHold(angle,throttle, Constants.AUTO_MAX_THROTTLE);
        prevValue = drivetrain.getEncoder();
    }

    @Override
    public void end(boolean interrupted) {
        if(interrupted) System.out.println("Error: Interrupted");
        System.out.println("Done with Drive distance command");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getEncoder()) > 5000;
    }
}
