package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.IntakeBalls;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.*;
import static frc.team5115.Robot.RobotContainer.joy;

public class Intake extends SubsystemBase {
    TalonSRX intake_m;
    double intakeSpeed = 0.3;

    public Intake() {
        intake_m = new TalonSRX(INTAKE_MOTOR_ID);
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




