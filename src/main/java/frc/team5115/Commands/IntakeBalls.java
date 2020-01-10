package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.Robot;

public class IntakeBalls extends CommandBase {
    public IntakeBalls() {
        addRequirements(Robot.intake);
        withTimeout(.9);
    }

    public void initialize() {
        Robot.intake.Inhale();
    }

    public void execute() {
    }

    public boolean isFinished() {
        return true;
    }
}
