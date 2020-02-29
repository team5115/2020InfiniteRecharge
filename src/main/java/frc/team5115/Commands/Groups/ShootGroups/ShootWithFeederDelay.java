
package frc.team5115.Commands.Groups.ShootGroups;

import edu.wpi.first.wpilibj2.command.*;
import frc.team5115.Commands.Feeder.moveCellsUntilSensor;
import frc.team5115.Commands.Shooter.Shoot;
import frc.team5115.Subsystems.Feeder;
import frc.team5115.Subsystems.Shooter;

import static frc.team5115.Configuration.Constants.FEEDER_TIME_OUT;

public class ShootWithFeederDelay extends SequentialCommandGroup {
    Shooter shooter;
    Feeder feeder;
    Command dealineCommand;
    ParallelDeadlineGroup TheShootingSquad;

    public ShootWithFeederDelay(Shooter x, Feeder y) {
        shooter = x;
        feeder = y;
        dealineCommand = new moveCellsUntilSensor(feeder);
        TheShootingSquad = new ParallelDeadlineGroup(
                new Shoot(shooter),
                dealineCommand
        );
        addCommands(
                new InstantCommand(shooter::shoot),
                new WaitCommand(3),
                TheShootingSquad
                new InstantCommand(shooter::shoot)
        );
        TheShootingSquad.setDeadline(dealineCommand);


    }
}