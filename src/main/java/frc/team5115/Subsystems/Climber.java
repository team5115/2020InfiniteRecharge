package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Climber.StopClimb;

import static frc.team5115.Constants.*;

public class Climber extends SubsystemBase {
    CANSparkMax winch;
    TalonSRX scissor;

    DigitalInput upper;
    DigitalInput lower;

    double climbspeed = -.75;

    public Climber(){
        winch = new CANSparkMax(WINCH_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        winch.restoreFactoryDefaults();

        scissor = new TalonSRX(SCISSOR_MOTOR_ID);

        upper = new DigitalInput(UPPER_LIMIT_ID);
        lower = new DigitalInput(LOWER_LIMIT_ID);

        setDefaultCommand(new StopClimb(this).perpetually());
        print();
    }

    public void ScissorUp(){
        scissor.set(ControlMode.PercentOutput, upper.get() ? 1 : 0);
    }

    public void ScissorDown(){
        scissor.set(ControlMode.PercentOutput, lower.get() ? -.25 : 0);
    }

    public void WinchDown(){
        winch.set(-climbspeed);
    }

    public void WinchUp() {
        winch.set(climbspeed);
    }

    public void StopClimb() {
        scissor.set(ControlMode.PercentOutput, 0);
        winch.set(0);
    }

    public void print() {
        System.out.println("upper.get() = " + upper.get());
        System.out.println("lower.get() = " + lower.get());
    }

}