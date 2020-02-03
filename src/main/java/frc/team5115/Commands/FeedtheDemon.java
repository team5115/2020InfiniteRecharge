package frc.team5115.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.RobotContainer;

public class FeedtheDemon extends CommandBase {
    Boolean sensor = true;
    public FeedtheDemon() {
        addRequirements(RobotContainer.feeder);

    }

    @Override
    public void execute() {
        //get sensor value

        if(sensor){
            RobotContainer.feeder.moveCells();
        }
        else{
            RobotContainer.feeder.stopCells();
        }

    }
}


