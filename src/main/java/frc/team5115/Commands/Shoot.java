package frc.team5115.Commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Robot.*;

public class Shoot extends CommandBase {

    public Shoot() {
        addRequirements(RobotContainer.shooter);

    }

    @Override
    public void initialize() {
        RobotContainer.shooter.shoot();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}