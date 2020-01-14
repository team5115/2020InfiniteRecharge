package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;

public class IntakeBalls extends CommandBase {
    public IntakeBalls() {
       addRequirements(RobotContainer.intake);

    }

    @Override
    public void initialize() {
        RobotContainer.intake.inhale();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
