package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Intake;

public class IntakeBalls extends CommandBase {
    Intake intake;

    public IntakeBalls(Intake intake) {
        this.intake = intake;
       addRequirements(intake);
    }

    @Override
    public void execute() {intake.inhale();
    }
}
