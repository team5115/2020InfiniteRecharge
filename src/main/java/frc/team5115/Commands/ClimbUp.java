package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Climber;

import static frc.team5115.Robot.RobotContainer.climber;

public class ClimbUp extends CommandBase{
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
