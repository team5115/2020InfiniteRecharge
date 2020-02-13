package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Climber.StopClimb;

import static frc.team5115.Constants.*;

public class Climber extends SubsystemBase {
    TalonSRX winch;
    TalonSRX scissor;
    double climbspeed = -0.75;

    public Climber(){
        winch = new TalonSRX(WINCH_MOTOR_ID);
        scissor = new TalonSRX(SCISSOR_MOTOR_ID);
        setDefaultCommand(new StopClimb(this).perpetually());
    }

    public void ScissorUp(){
        scissor.set(ControlMode.PercentOutput, -1);
    }

    public void ScissorDown(){
        scissor.set(ControlMode.PercentOutput, -climbspeed);
    }

    public void WinchDown(){
        winch.set(ControlMode.PercentOutput, -climbspeed);
    }

    public void StopClimb() {
        scissor.set(ControlMode.PercentOutput, 0);
        winch.set(ControlMode.PercentOutput, 0);
    }

}
