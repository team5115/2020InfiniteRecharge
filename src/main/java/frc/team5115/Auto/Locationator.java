package frc.team5115.Auto;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team5115.Constants.StartingConfiguration;
import frc.team5115.Robot.RobotContainer;
import frc.team5115.Subsystems.Drivetrain;


public class Locationator implements Subsystem {

    private AHRS navx; //turn baby.
    private double angle; //angle is total accumulated.
    Drivetrain drive;
    private double startAngle;
    private Loc2D currentLocation;

    public Locationator(RobotContainer x, Loc2D startingLocation, double startAngle) {
        navx = new AHRS(SPI.Port.kMXP);
        navx.reset(); //reset to the start orientation
        currentLocation = startingLocation;
        this.startAngle = startAngle;
        this.setDefaultCommand(new RunCommand(this::update));
    }

    public Locationator(RobotContainer x, StartingConfiguration startingConfiguration, double startAngle) {
        this(x, new Loc2D(startingConfiguration.getX(), startingConfiguration.getY()), startAngle);
    }

    /**
     * resets the navx yaw value to 0. Relative to the current position of the robot.
     */
    public void navxAngleReset() {
        navx.reset(); //reset to the field orientation
        System.out.println("Angle has been reset.");
    }

    /**
     * @return totalAccumulated Angle -gazillion to a gazillion
     */
    public double getAngle() {
        angle = navx.getAngle();
        return angle + startAngle;
    }

    public void update() {
        double forwardSpeed = drive.getSpeedInchesPerSecond()/20;
        double deltaY = Math.sin(Math.toRadians(getAngle())) * forwardSpeed; //converts from M/s to inches/sec then * 0.02 seconds to get deltaInches.
        double deltaX = Math.cos(Math.toRadians(getAngle())) * forwardSpeed;
        currentLocation.deltaX(deltaX);
        currentLocation.deltaY(deltaY);
    }

    public Loc2D getCurrentLocation() {
        return currentLocation;
    }

    public void setAngleAndLocation(double angle, double x, double y) {
        navx.reset();
        startAngle = angle;
        this.angle = navx.getAngle() + startAngle;
        System.out.println("angle = " + angle);
        currentLocation = new Loc2D(x, y);
    }

    public String toString(){
        return ("IMUCalC: CurrentAngle: " + (int) getAngle() + "|yLoc: " + (int) currentLocation.getY() + "|xLoc: " + (int) currentLocation.getX());
    }


}