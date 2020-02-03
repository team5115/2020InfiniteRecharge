package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;

public class ReverseIntake extends CommandBase {
    public ReverseIntake() {
        addRequirements(RobotContainer.intake);
    }

    @Override
    public void execute() {
        RobotContainer.intake.spitout();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
