package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Constants.*;

public class Climber extends SubsystemBase {
    TalonSRX winch;
    TalonSRX scissor;

    public Climber(){
        winch = new TalonSRX(CLIMBER_MOTOR_ID);
        scissor = new TalonSRX(8);
    }

    public static StartC
}
