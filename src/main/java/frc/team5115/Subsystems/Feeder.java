package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Feeder.FeedtheDemon;
import frc.team5115.Robot.RobotContainer;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import static frc.team5115.Constants.*;


public class Feeder extends SubsystemBase implements Loggable {
    TalonSRX feeder_m;
    double feedspeed = 0.25;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3( I2C.Port.kOnboard );
    int lowProximityBound = 134;
    int highProximityBound = 150;


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

    public void printDistanceValues() {
        double proximity = m_colorSensor.getProximity();
    }

    @Log
    public int getProximity (){
        System.out.println("m_colorSensor.getProximity() = " + m_colorSensor.getProximity());
        return m_colorSensor.getProximity();
    }

    @Log
    public boolean getProximityRange () {
        return getProximity() >= lowProximityBound && getProximity() <= highProximityBound;
    }


}




