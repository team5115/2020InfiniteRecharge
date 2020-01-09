package frc.team5115.Subsystems;

public class Limelight {

    int currentPipeline;

    public double getXAngle() {
        return 0;
    }

    public double getYAngle() {
        return 0;
    }

    public boolean hasTarget() {
        return false;
    }

    public void setPipeline(int pipe) {
        if (pipe != currentPipeline) { //if the new value is different than the past values, change it up.
            pipeline.setNumber(pipe);
            currentPipeline = pipe;
            System.out.println("Changed Pipeline to " + pipe);
        }
    }
}
