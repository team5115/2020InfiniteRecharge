package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import static frc.team5115.Constants.*;

public class Feeder extends SubsystemBase implements Loggable {
    VictorSPX feeder_m;
    double feedspeed = -0.8;
    private final ColorSensorV3 m_colorSensor = new ColorSensorV3( I2C.Port.kOnboard );
    int lowerIRBound = 134;
    int higherIRBound = 150;

    int ballCount = 0;
    boolean ballDetected = false;
    boolean increment = false;

    public void printDistanceValues() {
        double proximity = m_colorSensor.getProximity();
    }


    // import rev robotics color sensor V3 package
    // use getIR()?

    public Feeder() {
        feeder_m = new VictorSPX(FEEDER_MOTOR_ID);
    }

    public void moveCells() {
        feeder_m.set(ControlMode.PercentOutput, feedspeed);
    }

    public void moveCellsFancy(){
        increment = false;

        System.out.println("ballCount = " + ballCount);
        if (getProximityRange()) {
            feeder_m.set(ControlMode.PercentOutput, feedspeed);
            ballDetected = true;
            increment = true;
        }
        else {
            feeder_m.set(ControlMode.PercentOutput, 0);
            ballDetected = false;
        }

        if (increment) {
            ballCount += ballDetected ? 1 : 0;
        }
    }

    public void stopCells() {
        feeder_m.set(ControlMode.PercentOutput, 0);
    }

    public void spit() {
        feeder_m.set(ControlMode.PercentOutput, -feedspeed);
    }

    @Log
    public int getProximity (){
        System.out.println("m_colorSensor.getProximity() = " + m_colorSensor.getProximity());
        return m_colorSensor.getProximity();
    }

    @Log
    public boolean getProximityRange () {
        return ((getProximity() >= lowerIRBound) && (getProximity() <= higherIRBound));
    }

    public void incrementBallCount() {
        if (getProximityRange() && ballDetected) {
            ballCount++;
        }
        ballDetected = !ballDetected;
    }

    //public void decrementBallCount() { ballCount--; }

    public int getBallCount() {

        return ballCount;
    }

}