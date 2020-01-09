package frc.team5115.Auto;

/**
 * This interfect helps
 */
public interface DriveBase {
    /**
     * Stops Motors
     */
    void stop();

    /**
     * @param y a positive y moves the robot forward.
     * @param x a no preference for how X works.
     * @param throttle basic scalling
     */
    void drive(double y, double x, double throttle);

    /**
     * Sets the drivetrains target angle to the current angle from the navx.
     */
    void resetTargetAngle();

    /**
     * @param targetAngle the angle the robot wants to be at, given.
     * @param y the forward speed to pass through to drive.
     */
    void angleHold(double targetAngle, double y);


    void driveByWire(double x, double y);


    /**
     * @return gets the average speed from the encoders.
     */
    double getSpeedInchesPerSecond();
    default double getSpeedFeetPerSecond() {
        return getSpeedInchesPerSecond() / 12;
    }
    default double getSpeedMetersPerSecond() {
        return getSpeedInchesPerSecond() * 0.0833333333;
    }
}
