package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Shooter.NoShoot;

import static frc.team5115.Configuration.Constants.ACCELERATOR_MOTOR_ID;
import static frc.team5115.Configuration.Constants.SHOOTER_MOTOR_ID;

public class Shooter extends SubsystemBase {
    TalonSRX shooter_m;
    TalonSRX accelerator_m;

    public Shooter(){
        shooter_m = new TalonSRX(SHOOTER_MOTOR_ID);
        accelerator_m= new TalonSRX(ACCELERATOR_MOTOR_ID);
        setDefaultCommand(new NoShoot(this).perpetually());
    }

    public void shoot(){
        System.out.println("shooting...");
        shooter_m.set(ControlMode.PercentOutput, -.2);
        accelerator_m.set(ControlMode.PercentOutput, -.2);
    }

    public void stopShoot() {
        accelerator_m.set(ControlMode.PercentOutput, 0);
        shooter_m.set(ControlMode.PercentOutput, 0);
    }
}

