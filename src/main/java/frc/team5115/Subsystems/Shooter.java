package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static frc.team5115.Constants.*;

public class Shooter implements Subsystem {
    TalonSRX shooter;
    double intakeSpeed = 0.5;

    public void initDefaultCommand() {
    }

    public Shooter(){
        shooter = new TalonSRX(SHOOTER_MOTOR_ID);
    }

    public void shoot(){
        shooter.set(ControlMode.PercentOutput, intakeSpeed);
    }

    public static class ShootForTime extends CommandBase {
        Timer timer;
        Shooter shooter;

        public ShootForTime(Shooter shooter) {
            this.shooter = shooter;
            System.out.println("Shooters be shootin my dudes");
        }

        @Override

        public void initialize() {
            Timer timer = new Timer();
            timer.start();
            shooter.shoot();
        }

        @Override
        public boolean isFinished() {
            return false;
        }
    }
}