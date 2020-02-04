package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.IntakeBalls;
import frc.team5115.Commands.StopClimb;

import static frc.team5115.Constants.*;

public class Climber extends SubsystemBase {
    TalonSRX winch;
    TalonSRX scissor;
    double climbspeed = 0.5;

    public Climber(){
        winch = new TalonSRX(CLIMBER_MOTOR_ID);
        scissor = new TalonSRX(8);
        setDefaultCommand(new StopClimb(this).perpetually());
    }

    public void ClimbUp(){
        scissor.set(ControlMode.PercentOutput, -climbspeed);
        winch.set(ControlMode.PercentOutput, climbspeed);
    }

    public void StopClimb(){
        winch.set(ControlMode.PercentOutput, 0);
        scissor.set(ControlMode.PercentOutput, 0);
    }

    public void ClimbDown(){
        winch.set(ControlMode.PercentOutput, -climbspeed);
        scissor.set(ControlMode.PercentOutput, climbspeed);
    }

}
