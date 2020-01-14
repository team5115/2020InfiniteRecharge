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
        addRequirements(RobotContainer.shooter);

    }

    @Override
    public void initialize() {
        RobotContainer.shooter.exhale();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}