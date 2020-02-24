package frc.team5115.Commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Shooter;

public class NoShoot extends CommandBase {
    Shooter shooter;

    public NoShoot(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.stopShoot();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
