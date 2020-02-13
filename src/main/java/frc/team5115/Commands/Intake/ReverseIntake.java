package frc.team5115.Commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Intake;

public class ReverseIntake extends CommandBase {
    Intake intake;

    public ReverseIntake(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.spitout();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
