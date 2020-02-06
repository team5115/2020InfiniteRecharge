package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Intake.IntakeBalls;

import static frc.team5115.Constants.*;

public class Intake extends SubsystemBase {
    VictorSPX intake_m;
    double intakeSpeed = 0.3;

    public Intake() {
        intake_m = new VictorSPX(INTAKE_MOTOR_ID);
        setDefaultCommand(new IntakeBalls(this).perpetually());
    }

    public void inhale() {
        intake_m.set(ControlMode.PercentOutput, intakeSpeed);
    }

    public void spitout(){
        intake_m.set(ControlMode.PercentOutput, -intakeSpeed);
    }

    public void driverIntake(){
        intake_m.set(ControlMode.PercentOutput, intakeSpeed + 0.2);
    }

}




