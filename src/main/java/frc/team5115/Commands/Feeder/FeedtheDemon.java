package frc.team5115.Commands.Feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team5115.Subsystems.Feeder;

import static frc.team5115.Configuration.Constants.FULL_CAPACITY;

public class FeedtheDemon extends CommandBase {
    boolean sensor = false;
    Feeder feeder;

    public FeedtheDemon(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(feeder);
    }

    @Override
    public void execute() {
        feeder.updateBallCount();
        //get sensor value
        if (feeder.getBallCount() < FULL_CAPACITY) {
//            if (feeder.getBallPresentInFeeder()) {
//                new FunctionalCommand(
//                        feeder::debug,
//                        () -> feeder.moveCells(),
//                        () -> feeder.stopCells(),
//                        () -> !feeder.getBallPresentInFeeder()
//                        feeder
//                ).deadlineWith(new WaitCommand(1)).schedule();
//            }
        }
        else {
            feeder.stopCells();
        }
    }
}


