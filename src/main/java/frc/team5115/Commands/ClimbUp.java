package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Climber;

public class ClimbUp extends CommandBase{
    Climber climber;

    public ClimbUp(Climber climber) {
        this.climber = climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {climber.ClimbDown();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
