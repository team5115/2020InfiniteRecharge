package frc.team5115.Configuration;
import edu.wpi.first.wpilibj.Timer;

public class Heartbeat {

    private double start;
    private double checkpoint;
    private double difference;

    public void start(){
        start = Timer.getFPGATimestamp();
    }

    public void check(){
        difference = Timer.getFPGATimestamp() - checkpoint;
        checkpoint = Timer.getFPGATimestamp();
    }

    public double getAbsolute(){
        return Timer.getFPGATimestamp() - start;

    }

    public double getDifference(){
        return difference;
    }
}
