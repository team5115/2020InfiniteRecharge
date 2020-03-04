package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Feeder.FeedtheDemon;
import io.github.oblarg.oblog.Loggable;

import static frc.team5115.Configuration.Constants.*;

public class Feeder extends SubsystemBase implements Loggable {
    VictorSPX feeder;
    ColorSensorV3 feederColorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    ColorSensorV3 shooterColorSensor = new ColorSensorV3(I2C.Port.kMXP);

    int ballCount;

    boolean isBallFeeder;
    boolean isBallShooter;

    public Feeder() {
        feeder = new VictorSPX(FEEDER_MOTOR_ID);

        feederColorSensor.configureProximitySensor(ColorSensorV3.ProximitySensorResolution.kProxRes8bit, ColorSensorV3.ProximitySensorMeasurementRate.kProxRate50ms);
        shooterColorSensor.configureProximitySensor(ColorSensorV3.ProximitySensorResolution.kProxRes8bit, ColorSensorV3.ProximitySensorMeasurementRate.kProxRate50ms);

        setDefaultCommand(new FeedtheDemon(this).perpetually());
        //ballCount = 0;
    }

    public void moveCells() {
        feeder.set(ControlMode.PercentOutput, FEEDER_SPEED);
    }

    public void stopCells() {
        feeder.set(ControlMode.PercentOutput, 0);
    }

    public void spit() {
        feeder.set(ControlMode.PercentOutput, -FEEDER_SPEED);
    }

    public void updateBallCount() {
        incrementBallCount();
        decrementBallCount();
    }

    public void incrementBallCount() {
        if(!isBallFeeder && getBallPresentInFeeder()) {
            ballCount++;
            isBallFeeder = true;
        }
        else isBallFeeder = getBallPresentInFeeder();
    }

    public void decrementBallCount() {
        if (!isBallShooter && getBallPresentInShooter()) {
            ballCount--;
            isBallShooter = true;
        }
        else isBallShooter = getBallPresentInShooter();
    }

    public boolean getBallPresentInFeeder() { return feederColorSensor.getProximity() > FEEDER_PROXIMITY_BOUND; }

    public boolean getBallPresentInShooter() { return shooterColorSensor.getProximity() > SHOOTER_PROXIMITY_BOUND; }

    public int getBallCount() {
        return ballCount;
    }


    public void debug() {
//        System.out.println("feederColorSensor.getProximity() = " + feederColorSensor.getProximity());
//        System.out.println("shooterColorSensor.getProximity() = " + shooterColorSensor.getProximity());
//        System.out.println("getBallPresentInFeeder() = " + getBallPresentInFeeder());
//        System.out.println("getBallPresentInShooter() = " + getBallPresentInShooter());
    }

    public void reset() {
        ballCount = 0;
    }
}