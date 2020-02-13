package frc.team5115.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Constants;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

public class AutoSeries extends SequentialCommandGroup {

    public AutoSeries(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight) {

        limelight.setPipeline(Constants.Pipeline.DriveCamera);

        final Loc2D overLineLocation = new Loc2D(
                locationator.getCurrentLocation().getX(),  //goes strait forward.
                120);
        final Loc2D afterShootLocation = locationator.getCurrentLocation();
        //addCommands((new RunCommand(limelight::debug).alongWith( //note this removes the other commands from ever being added. Make sure to format the 'along with' to make them run concurrently.


        //These commands do a basic auto series.
        addCommands(
                new ShootHighGoal(drivetrain,
                        locationator,
                        shooter,
                        limelight)
                //new PickupBallAuto(drivetrain,locationator,limelight)
        );
    }
}