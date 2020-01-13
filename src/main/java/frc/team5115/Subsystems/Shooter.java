package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Constants.*;

public class Shooter extends SubsystemBase {
    TalonSRX shooter;
    double intakeSpeed = 0.5;

    public Shooter(){
        shooter = new TalonSRX(SHOOTER_MOTOR_ID);
    }

    public void Inhale(){
        shooter.set(ControlMode.PercentOutput, intakeSpeed);
    }

}
