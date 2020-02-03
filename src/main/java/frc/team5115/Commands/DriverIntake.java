package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;

public class DriverIntake extends CommandBase {
    public DriverIntake() {
        addRequirements(RobotContainer.intake);

    }

    @Override
    public void execute() {
        RobotContainer.intake.driverIntake();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
