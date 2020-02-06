package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;


import static frc.team5115.Constants.*;

public class Shooter extends SubsystemBase implements Loggable {
    TalonSRX shooter_m;
    TalonSRX accelerator_m;

    SimpleMotorFeedforward shooter_s;
    SimpleMotorFeedforward accelerator_s;

    double ks = 1.02;
    double kv = 3.44;
    double ka = 0.452;

    @Log
    double targetVelocity = 1000;

    public Shooter(){
        shooter_m = new TalonSRX(SHOOTER_MOTOR_ID);
        accelerator_m= new TalonSRX(ACCERLERATOR_MOTOR_ID);

        shooter_s = new SimpleMotorFeedforward(ks, kv, ka);
        accelerator_s = new SimpleMotorFeedforward(ks, kv, ka);
    }

    public void shoot(){
        shooter_m.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

        shooter_m.setSensorPhase(true);


        /* Config the peak and nominal outputs */
        shooter_m.configNominalOutputForward(0, Constants.kTimeoutMs);
        shooter_m.configNominalOutputReverse(0, Constants.kTimeoutMs);
        shooter_m.configPeakOutputForward(1, Constants.kTimeoutMs);
        shooter_m.configPeakOutputReverse(-1, Constants.kTimeoutMs);

        /* Config the Velocity closed loop gains in slot0 */
        shooter_m.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kP, Constants.kTimeoutMs);
        shooter_m.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kI, Constants.kTimeoutMs);
        shooter_m.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocity.kD, Constants.kTimeoutMs);
        System.out.println("shooting");
        double targetVelocity = 1000; //496 ticks per revelution

        accelerator_m.set(ControlMode.Velocity,(1500), DemandType.ArbitraryFeedForward, accelerator_s.calculate(1.337786)/12);
        shooter_m.set(ControlMode.Velocity, (1500), DemandType.ArbitraryFeedForward, shooter_s.calculate(-1.33776)/12);
    }

    @Log
    public double getCurrentVelocity(){
        return shooter_m.getSelectedSensorVelocity();
    }

    @Log
    public double getError(){
        return shooter_m.getClosedLoopError();
    }
}

