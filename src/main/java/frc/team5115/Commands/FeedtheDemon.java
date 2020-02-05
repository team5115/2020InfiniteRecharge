package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Feeder;

public class FeedtheDemon extends CommandBase {
    boolean sensor = true;
    Feeder feeder;

    public FeedtheDemon(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(feeder);

    }

    @Override
    public void execute() {
        //get sensor value

        if(sensor){
            feeder.moveCells();
        }
        else{
            feeder.stopCells();
        }

    }
}


