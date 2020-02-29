package frc.team5115.Commands.Feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Feeder;

import static frc.team5115.Configuration.Constants.FULL_CAPACITY;

public class moveCellsUntilSensor extends CommandBase {
    Feeder feeder;

    public moveCellsUntilSensor(Feeder x) {
        feeder = x;
        addRequirements(feeder);
    }

    @Override
    public void execute() {
        feeder.moveCells();
    }

    public boolean isFinished(){
        return feeder.getBallPresentInShooter();
    }
}