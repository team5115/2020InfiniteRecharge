
package frc.team5115.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.AutoCommands.DriveDistance;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Subsystems.*;
import frc.team5115.Auto.Loc2D;

public class AutoSeries extends SequentialCommandGroup {
    Drivetrain drivetrain;
    Locationator locationator;



    public AutoSeries(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;


        final Loc2D overLineLocation = new Loc2D(
                locationator.getCurrentLocation().getX(),  //goes strait forward.
                100);

        final Loc2D afterShootLocation = null;

        addCommands(
                // Drive to the new distance.
                new DriveDistance(overLineLocation,
                        drivetrain,
                        locationator),

                // Release the hatch
                new ShootHighGoal(drivetrain,
                        locationator,
                        shooter,
                        limelight),

                // Drive backward the specified distance
                new DriveDistance(afterShootLocation,
                        drivetrain,
                        locationator)
        );
    }
}
