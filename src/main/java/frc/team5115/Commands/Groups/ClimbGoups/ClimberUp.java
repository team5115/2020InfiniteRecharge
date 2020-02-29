package frc.team5115.Commands.Groups.ClimbGoups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.team5115.Subsystems.Climber;
import frc.team5115.Subsystems.Drivetrain;

public class ClimberUp extends ParallelCommandGroup {
    Climber climber;
    Drivetrain drivetrain;

    public ClimberUp(Climber climber, Drivetrain drivetrain) {
        this.climber = climber;
        this.drivetrain = drivetrain;

        addRequirements(climber, drivetrain);

        addCommands(
                new InstantCommand(climber::ScissorUp),
                new InstantCommand(drivetrain::setServoAngleClimbing)
        );
    }
}
