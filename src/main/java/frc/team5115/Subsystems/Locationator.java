package frc.team5115.Subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Constants.StartingConfiguration;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.startY;

public class Locationator implements Subsystem {

    private AHRS navx; //turn baby.
    private double angle; //angle is total accumulated.
    RobotContainer x;
    private double startAngle;
    private Loc2D currentLocation;

    public Locationator(RobotContainer x, Loc2D startingLocation, double startAngle) {
        navx = new AHRS(SPI.Port.kMXP);
        navx.reset(); //reset to the start orientation
        this.x = x;
        currentLocation = startingLocation.clone();
        this.startAngle = startAngle;
        this.setDefaultCommand(new cmd(this::runTick, this));
    }

    public Locationator(RobotContainer x, StartingConfiguration startingConfiguration, double startAngle) {

        this(
                x,
                new Loc2D(startingConfiguration.getX(), startY),
                startAngle
        );
    }

    /**
     * resets the navx yaw value to 0. Relative to the current position of the robot.
     */
    public void navxAngleReset() {
        navx.reset(); //reset to the field orientation
        System.out.println("Angle has been reset.");
        System.out.println(navx.getYaw() + " = 0");
    }

    /**
     * @return totalAccumulated Angle -gazillion to a gazillion
     */
    public double getAngle() {
        angle = navx.getAngle();
        return angle + startAngle;
    }

    public void runTick() {
        //System.out.println("getAngle() = " + getAngle());
        double forwardSpeed = x.drivetrain.getSpeedInchesPerSecond()/20;
        double deltaY = Math.sin(Math.toRadians(getAngle())) * forwardSpeed; //converts from M/s to inches/sec then * 0.02 seconds to get deltaInches.
        double deltaX = Math.cos(Math.toRadians(getAngle())) * forwardSpeed;
        currentLocation.deltaX(deltaX);
        currentLocation.deltaY(deltaY);

        //System.out.println("IMUCalC: CurrentAngle: " + (int) getAngle() + "|yLoc: " + (int) currentLocation.getY() + "|xLoc: " + (int) currentLocation.getX());
        //System.out.println("Delta X: " + deltaX + "|Delta Y: " + deltaY);
        //System.out.println("YVelocity: " + forwardSpeed);
    }

    public Loc2D getCurrentLocation() {
        return currentLocation;
    }

    public class cmd extends RunCommand {

        /**
         * Creates a new RunCommand.  The Runnable will be run continuously until the command
         * ends.  Does not run when disabled.
         *
         * @param toRun        the Runnable to run
         * @param requirements the subsystems to require
         */
        public cmd(Runnable toRun, Subsystem... requirements) {
            super(toRun, requirements);
        }

        public void execute() {
            runTick();
        }
    }

    public void setAngleAndLocation(double angle, double x, double y) {
        navx.reset();
        startAngle = angle;
        this.angle = navx.getAngle() + startAngle;
        System.out.println("angle = " + angle);
        currentLocation = new Loc2D(x, y);
    }
}