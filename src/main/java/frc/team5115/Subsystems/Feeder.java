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

    @Log
    int ballCount = 0;

    private final ColorSensorV3 m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    int lowerIRBound = 134;
    int higherIRBound = 150;

    public Feeder() {
        feeder_m = new VictorSPX(FEEDER_MOTOR_ID);
        setDefaultCommand(new InstantCommand(this::store));
        ballLastTime = false;
    }

    /**
     * This method allows balls to enter and be stored, but only if the ball count allows them too.
     */
    public void store() {
        countBalls();
        if (isBallPresent() && ballCount <= 2) { //if a ball is present, move the feeder. If we have more than two balls, do not move the cells.
            feeder_m.set(ControlMode.PercentOutput, FEEDER_STORE_SPEED);
        }
    }

    /**
     * This method allows balls to enter and be stored regardless of ball count.
     */
    public void storeRegardless() {
        countBalls();
        if (isBallPresent()) { //if a ball is present, move the feeder. If we have more than two balls, do not move the cells.
            feeder_m.set(ControlMode.PercentOutput, FEEDER_STORE_SPEED);
        }
    }


    /**
     * The flush method allows for balls to be removed into the shooter. This is most likely for shooting.
     */
    public void flush() {
        feeder_m.set(ControlMode.PercentOutput, FEEDER_FLUSH_SPEED);
    }

    /**
     * The spit method allows for balls to be removed out the way they came in. Literally spit at full speed.
     */
    public void spit() {
        feeder_m.set(ControlMode.PercentOutput, -FEEDER_FLUSH_SPEED);
    }

    boolean ballLastTime;

    public void countBalls() {
        boolean ballCurrentlyBlocking = isBallPresent();
        if (!ballLastTime && ballCurrentlyBlocking) {
            ballCount++;
        }
        ballLastTime = ballCurrentlyBlocking;
    }

    @Log
    public int getProximity() {
        return m_colorSensor.getProximity();
    }

    @Log
    public boolean isBallPresent() {
        return getProximity() >= lowerIRBound && getProximity() <= higherIRBound;
    }

}




