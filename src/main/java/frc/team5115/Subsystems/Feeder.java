package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Commands.Feeder.FeedtheDemon;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

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
        setDefaultCommand(new FeedtheDemon(this).perpetually());
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

    @Log
    public boolean getBallPresentInFeeder() { return feederColorSensor.getProximity() > FEEDER_PROXIMITY_BOUND; }

    public boolean getBallPresentInShooter() { return shooterColorSensor.getProximity() > SHOOTER_PROXIMITY_BOUND; }

    public int getBallCount() {
        return ballCount;
    }

}