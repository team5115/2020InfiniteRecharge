package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Climber;

public class StopClimb extends CommandBase {

    Climber climber;

    public StopClimb(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.StopClimb();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
