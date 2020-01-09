package frc.team5115.Auto.AutoCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Auto.Loc2D;
public class DriveDistance extends CommandBase {

    Loc2D loc2D;
    Drivetrain drivetrain;
    Locationator locationator;

    public DriveDistance(Loc2D loc2D, Drivetrain drivetrain, Locationator locationator) {
        this.loc2D = loc2D;
        this.drivetrain = drivetrain;
        this.locationator = locationator;
    }
}
