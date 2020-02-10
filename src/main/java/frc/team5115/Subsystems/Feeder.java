package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.team5115.Constants.INTAKE_MOTOR_ID;

public class Feeder extends SubsystemBase {
    TalonSRX feeder_m;
    double feedspeed = 0.3;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3( I2C.Port.kOnboard );

    public void printDistanceValues() {

        double proximity = m_colorSensor.getProximity();
        SmartDashboard.putNumber("proximity", proximity);
    }


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




