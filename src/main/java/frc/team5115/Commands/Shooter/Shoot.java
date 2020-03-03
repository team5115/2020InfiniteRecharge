package frc.team5115.Commands.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Shooter;

public class Shoot extends CommandBase {
    Shooter shooter;

    public Shoot(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.shoot();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}