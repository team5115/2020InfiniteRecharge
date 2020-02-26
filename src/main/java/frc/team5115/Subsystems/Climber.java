package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Climber.StopClimb;

import static frc.team5115.Configuration.Constants.*;

public class Climber extends SubsystemBase {
    CANSparkMax winch;

    TalonSRX scissor;

    double climbspeed = -.75;

    public Climber(){
        winch = new CANSparkMax(WINCH_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        winch.restoreFactoryDefaults();

        scissor = new TalonSRX(SCISSOR_MOTOR_ID);

        scissor.overrideLimitSwitchesEnable(true);
        scissor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        scissor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);

        setDefaultCommand(new StopClimb(this).perpetually());
        print();
    }

    public boolean getUpper() {
        return scissor.isFwdLimitSwitchClosed() == 0;
    }

    public boolean getLower() {
        return scissor.isRevLimitSwitchClosed() == 0;
    }

    public void ScissorUp(){
        scissor.set(ControlMode.PercentOutput, getUpper() ? 1 : 0);
    }

    public void ScissorDown(){
        scissor.set(ControlMode.PercentOutput, getLower() ? -.25 : 0);
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
        System.out.println("upper.get() = " + getUpper());
        System.out.println("lower.get() = " + getLower());
    }

}