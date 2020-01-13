package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Constants.*;

public class Intake extends SubsystemBase {
    TalonSRX frontIntake;
    double intakeSpeed = 0.5;

    public Intake(){
        frontIntake = new TalonSRX(INTAKE_MOTOR_ID);
    }

    public void Inhale(){
        frontIntake.set(ControlMode.PercentOutput, intakeSpeed);
    }

}
