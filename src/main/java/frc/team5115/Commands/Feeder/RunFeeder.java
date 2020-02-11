package frc.team5115.Commands.Feeder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Feeder;

public class RunFeeder extends CommandBase {
    Feeder feeder;

    public RunFeeder(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(feeder);
    }
}
