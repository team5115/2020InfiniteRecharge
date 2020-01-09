
package frc.team5115.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Subsystems.*;
import frc.team5115.autotools.Loc2D;

public class AutoSeries extends SequentialCommandGroup {
    Drivetrain drivetrain;
    Locationator locationator;



    public AutoSeries(Drivetrain drivetrain, Locationator locationator) {
        this.drivetrain = drivetrain;
        this.locationator = locationator;


        final Loc2D overLine = new Loc2D(, 100);

        addCommands(
                // Drive to the new distance.
                new DriveDistance(new Loc2D(),
                                drivetrain,
                                locationator);
//todome fix the auto series.
                // Release the hatch
                new ShootBalls(hatch),

                // Drive backward the specified distance
                new DriveDistance(AutoConstants.kAutoBackupDistanceInches, -AutoConstants.kAutoDriveSpeed,
                        drive));
    }
}
