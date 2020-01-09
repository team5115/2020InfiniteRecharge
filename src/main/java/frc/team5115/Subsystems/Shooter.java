package frc.team5115.Subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Shooter implements Subsystem {
    void shoot() {
        //Don't know how its gonna work yet
    }
    void stopShooting() {
        //Don't know how its gonna work yet
    }

    public static class Shoot extends CommandBase {
        //todome fill this in.
        Timer timer;
        Shooter shooter;
        int seconds;

        public Shoot(int seconds, Shooter shooter) {
            this.seconds = seconds;
            this.shooter = shooter;
        }

        @Override
        public void initialize() {
            timer.start();
            shooter.shoot();
        }

        @Override
        public void execute() {
        }

        @Override
        public void end(boolean interrupted) {
            shooter.stopShooting();
        }

        @Override
        public boolean isFinished() {
            return timer.get() > seconds;
        }
    }


}
