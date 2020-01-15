package frc.team5115.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import static frc.team5115.Constants.*;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

public class Limelight {

    NetworkTableEntry pipeline;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry tv;

    int currentPipeline = 0;
    public Limelight() {
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        tx = networkTableInstance.getEntry("tx");
        ty = networkTableInstance.getEntry("ty");
        tv = networkTableInstance.getEntry("tv");
        pipeline = networkTableInstance.getEntry("pipeline");
        setPipeline(2);
    }

    public double getXAngle() {
        return tx.getDouble(0);
    }

    public double getYAngle() {
        return ty.getDouble(0);
    }

    public boolean hasTarget() {
        return tv.getDouble(0) > 0.5;
    }

    public void setPipeline(int pipe) {
        if (pipe != currentPipeline) { //if the new value is different than the past values, change it up.
            pipeline.setNumber(pipe);
            currentPipeline = pipe;
            System.out.println("Changed Pipeline to " + pipe);
        }
    }

    public double calculateDistanceFromBase() {
        return (HIGH_GOAL_HEIGHT - CAMERA_HEIGHT) / tan(toRadians(getYAngle() + CAMERA_ANGLE)); //
    }

    public void debug() {
        System.out.println("tx:" + getXAngle());
        System.out.println("ty:" + getYAngle());
        System.out.println("tv:" + hasTarget());
    }
}
