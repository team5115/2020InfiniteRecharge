package frc.team5115.autotools;

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
     * @param currentAngle the angle reported from the navx, given.
     * @param targetAngle the angle the robot wants to be at, given.
     * @param y the forward speed to pass through to drive.
     */
    void angleHold(double currentAngle, double targetAngle, double y);


    /**
     * @param targetAngle trys to lower this angle to 0.
     */
    default void angleHold(double targetAngle) {
        this.angleHold(0, targetAngle, 0); //this should work.
    }

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
