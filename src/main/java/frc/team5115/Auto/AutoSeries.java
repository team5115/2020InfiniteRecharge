
package frc.team5115.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.AutoCommands.DriveDistance;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

public class AutoSeries extends SequentialCommandGroup {
    Locationator locationator;

    public AutoSeries(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {

        this.locationator = locationator;

        final Loc2D overLineLocation = new Loc2D(
                locationator.getCurrentLocation().getX(),  //goes strait forward.
                Constants.LINE_TARGET_Y);

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

    public Locationator getLocationator() {
        return this.locationator;
    }
}
