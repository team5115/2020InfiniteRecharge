package frc.team5115.Subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5115.Constants;

import static frc.team5115.Constants.*;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

public class Limelight {

    NetworkTableEntry pipeline;
    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry tv;
    private NetworkTableEntry ta;

    int currentPipeline = 0;
    public Limelight() {
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        tx = networkTableInstance.getEntry("tx");
        ty = networkTableInstance.getEntry("ty");
        tv = networkTableInstance.getEntry("tv");
        ta = networkTableInstance.getEntry("ta");
        pipeline = networkTableInstance.getEntry("pipeline");
        pipeline.setValue(2);
        setPipeline(Pipeline.GreenLedMode);
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

    public double getArea() {
        return ta.getDouble(0);
    }

    public void setPipeline(int newPipe) {
        if (newPipe != currentPipeline) { //if the new value is different than the past values, change it up.
            pipeline.setNumber(newPipe);
            currentPipeline = newPipe;
            System.out.println("Changed Pipeline to " + newPipe);
        }
    }
    public void setPipeline(Constants.Pipeline pipeline) {
        setPipeline(pipeline.getPipelineNumber());
    }

    public double calculateDistanceFromBase() {
        //System.out.println("Height difference: " + (HIGH_GOAL_HEIGHT - CAMERA_HEIGHT));
        //System.out.println("Total angle: " + (getYAngle() + CAMERA_ANGLE));
        return (HIGH_GOAL_HEIGHT - CAMERA_HEIGHT) / tan(toRadians(getYAngle() + CAMERA_ANGLE));
    }

    public void debug() {
        System.out.println("tx:" + getXAngle());
        System.out.println("ty:" + getYAngle());
        System.out.println("tv:" + hasTarget());
    }

    public double calculateDistanceFromBall() {
        if(getYAngle() + CAMERA_ANGLE> 0) { return 0; //if it doesnt make any sense, get rid of it.
        }
        double distanceFromAngle = (CAMERA_HEIGHT) / -tan(toRadians(getYAngle() + CAMERA_ANGLE));
        if(getArea() > 10) { //if its greater than 10, we should probably just go with the area.
            System.out.println("You better do a DESMOS thing to determine how the area of the ball relates to distance.");
            return 10e10;
        }
        return distanceFromAngle;
    }
}
