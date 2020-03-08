package frc.team5115.Auto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Auto.AutoCommands.AssistedShootHighGoal;
import frc.team5115.Auto.AutoCommands.DriveDistance;
import frc.team5115.Configuration.Constants;
import frc.team5115.Subsystems.*;

public class AutoSeries extends SequentialCommandGroup {

    public AutoSeries(Drivetrain drivetrain, Locationator locationator, Shooter shooter, Limelight limelight, Feeder feeder, Joystick joystick) {

        limelight.setPipeline(Constants.Pipeline.DriveCamera);

        final Loc2D overLineLocation = new Loc2D(
                locationator.getCurrentLocation().getX(),  //goes straight forward.
                200);
        final Loc2D afterShootLocation = locationator.getCurrentLocation();
        //addCommands((new RunCommand(limelight::debug).alongWith( //note this removes the other commands from ever being added. Make sure to format the 'along with' to make them run concurrently.


        //These commands do a basic auto series.
        addCommands(
                new DriveDistance(overLineLocation, drivetrain, locationator).withTimeout(2),
                //new RunCommand(drivetrain::driveStraight).withTimeout(2),
                new SequentialCommandGroup(
                        new RunCommand(shooter::shoot).withTimeout(2),
                        new InstantCommand(shooter::shoot).alongWith(new InstantCommand(feeder::moveCells)).alongWith(new InstantCommand(limelight::driverPipline))
                ).withTimeout(2)
                //new ShootHighGoal(drivetrain, locationator, shooter, limelight)
                //new PickupBallAuto(drivetrain,locationator,limelight)
        );
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
}