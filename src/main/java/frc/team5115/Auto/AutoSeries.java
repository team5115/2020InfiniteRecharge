
package frc.team5115.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.AutoCommands.DriveDistance;
import frc.team5115.Auto.AutoCommands.PickupBall;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

public class AutoSeries extends SequentialCommandGroup {

    public AutoSeries(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {

        System.out.println("Creating auto series.");
        final Loc2D overLineLocation = new Loc2D(
                locationator.getCurrentLocation().getX(),  //goes strait forward.
                150);
        final Loc2D afterShootLocation = locationator.getCurrentLocation();
        //addCommands((new RunCommand(limelight::debug).alongWith( //note this removes the other commands from ever being added. Make sure to format the 'along with' to make them run concurrently.


        //These commands do a basic auto series.
        if(false) {
        addCommands(
                // Drive to the new distance.

                new DriveDistance(overLineLocation,
                        drivetrain,
                        locationator),
                // Release the hatch
                //*/
                new ShootHighGoal(drivetrain,
                        locationator,
                        shooter,
                        limelight)
                /*
                // Drive backward the specified distance
                new DriveDistance(afterShootLocation,
                        drivetrain,
                        locationator)
                 */
        );
        } else {
            addCommands(
                    new PickupBall(drivetrain, locationator, limelight)
            );
        }
    }
}
