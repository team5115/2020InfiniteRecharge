package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.*;
import static frc.team5115.Robot.RobotContainer.joy;

public class Feeder extends SubsystemBase {
    TalonSRX feeder_m;
    double feedspeed = 0.3;
    
    

    // import rev robotics color sensor V3 package
    // use getIR()?

    public Feeder() {
        feeder_m = new TalonSRX(INTAKE_MOTOR_ID);
    }

    public void moveCells() {

        feeder_m.set(ControlMode.PercentOutput, feedspeed);
    }

    public void stopCells(){

        feeder_m.set(ControlMode.PercentOutput, 0);
    }


}




