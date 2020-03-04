package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Configuration.Constants.*;

public class Intake extends SubsystemBase {
    VictorSPX intake_m;
    double intakeSpeed = -0.5;

    public Intake() {
        intake_m = new VictorSPX(INTAKE_MOTOR_ID);
    }

    public void inhale() {
        intake_m.set(ControlMode.PercentOutput, intakeSpeed);
    }

    public void spitout(){
        intake_m.set(ControlMode.PercentOutput, -intakeSpeed);
    }

    public void driverIntake(){
        intake_m.set(ControlMode.PercentOutput, intakeSpeed - 0.2);
    }

    public void stopIntake() {
        intake_m.set(ControlMode.PercentOutput, 0);
    }
}




