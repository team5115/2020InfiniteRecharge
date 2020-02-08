package frc.team5115.Commands.Groups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Subsystems.Climber;

public class ReduceTheClimberIntoItsFeebleState extends SequentialCommandGroup {
    Climber climber;
    public ReduceTheClimberIntoItsFeebleState(Climber climber) {
        this.climber = climber;
        addCommands(
                new InstantCommand(climber::ScissorDown).withTimeout(2),
                new ParallelCommandGroup(
                        new InstantCommand(climber::ScissorDown),
                        new InstantCommand(climber::WinchDown)
                )
        );
    }
}