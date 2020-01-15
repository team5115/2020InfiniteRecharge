package frc.team5115.Subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team5115.Auto.DriveBase;
import frc.team5115.Auto.Loc2D;
import frc.team5115.Auto.StartingConfiguration;

import static frc.team5115.Constants.startY;

public class Locationator implements Subsystem {

    private AHRS navx; //turn baby.
    private double angle; //angle is total accumulated.
    private double yaw; //relative to start, from -180 to 180.
    private DriveBase driveBase;
    private final double startAngle;
    private Loc2D currentLocation;

    public Locationator(DriveBase x, Loc2D startingLocation, double startAngle) {
        navx = new AHRS(SPI.Port.kMXP);
        navx.reset(); //reset to the start orientation
        driveBase = x;
        currentLocation = startingLocation.clone();
        this.startAngle = startAngle;
        this.setDefaultCommand(new cmd(this::runTick, this));
    }

    public Locationator(DriveBase x, StartingConfiguration startingConfiguration, double startAngle) {

        this(x,
                new Loc2D(StartingConfiguration.getX(startingConfiguration), startY),
                startAngle);
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
        return angle + startAngle;
    }

    /**
     * @return angle -180 to 180
     */
    public double getYaw() {
        return (yaw + startAngle) % 360;
    }


    public void runTick() {

        double forwardSpeed = driveBase.getSpeedInchesPerSecond()/20;
        double deltaY = Math.sin(Math.toRadians(getAngle())) * forwardSpeed; //converts from M/s to inches/sec then * 0.02 seconds to get deltaInches.
        double deltaX = Math.cos(Math.toRadians(getAngle())) * forwardSpeed;
        currentLocation.deltaX(deltaX);
        currentLocation.deltaY(deltaY);

//        System.out.println("IMUCalC: CurrentAngle: " + (int) currentAngle + "|yLoc: " + (int) yLoc + "|xLoc: " + (int) xLoc);
//        System.out.println("Delta X: " + deltaX + "|Delta Y: " + deltaY);
//        System.out.println("YVelocity: " + forwardSpeed);
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
}