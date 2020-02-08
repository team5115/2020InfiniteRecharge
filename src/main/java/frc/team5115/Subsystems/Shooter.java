package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
        accelerator_m= new TalonSRX(ACCELERATOR_MOTOR_ID);
    }

    public void shoot(){
        shooter_m.set(ControlMode.PercentOutput, -.8);
        accelerator_m.set(ControlMode.PercentOutput, -.8);
    }

    public void stopShoot() {
        accelerator_m.set(ControlMode.PercentOutput, 0);
        shooter_m.set(ControlMode.PercentOutput, 0);
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

