package frc.team5115.Commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.*;

/**
 * An example command that uses an example subsystem.
 */
public class Shoot extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    public Shoot() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Robot.shooter);

    }

    @Override
    public void initialize() {
        Robot.shooter.Inhale();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}