package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Constants.*;

public class Intake extends SubsystemBase {
    VictorSPX intake_m;

    public Intake() {
        intake_m = new VictorSPX(INTAKE_MOTOR_ID);
        setDefaultCommand(new InstantCommand(this::inhale));
    }

    public void inhale() {
        intake_m.set(ControlMode.PercentOutput, INTAKE_INHALE_SPEED);
    }

    public void spitout(){
        intake_m.set(ControlMode.PercentOutput, -INTAKE_INHALE_SPEED);
    }

    public void stopIntake() {
        intake_m.set(ControlMode.PercentOutput, 0);
    }
}




