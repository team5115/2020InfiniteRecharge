package frc.team5115.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team5115.Configuration.Wrappers.Ultrasonic;
import frc.team5115.Subsystems.Feeder;
import io.github.oblarg.oblog.Logger;

import static frc.team5115.Configuration.Constants.startingConfiguration;


public class Robot extends TimedRobot {
    private Command autoCommand;
    private RobotContainer robotContainer;
    Ultrasonic ultrasonic;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        Logger.configureLoggingAndConfig(this, false);
        robotContainer = new RobotContainer();
        ultrasonic = new Ultrasonic(0);
    }

    @Override
    public void robotPeriodic() {
        Logger.updateEntries();
        CommandScheduler.getInstance().run();
        System.out.println("ultrasonic.getUltrasonicDistanceInches() = " + ultrasonic.getUltrasonicDistanceInches());
        SmartDashboard.putBoolean("In Range", ultrasonic.getUltrasonicDistanceInches() > 20 && ultrasonic.getUltrasonicDistanceInches() < 30);
    }

    @Override
    public void disabledInit() {
        autoCommand = robotContainer.getAutonomousCommand();
        autoCommand.cancel();
        robotContainer.refreshRobot();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        robotContainer.refreshRobot();
        autoCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autoCommand != null) {
            robotContainer.locationator.setAngleAndLocation(90, startingConfiguration.getX(), 30);
            autoCommand.schedule();
            System.out.println("Scheduling auto command");
        } else System.out.println("Boy you better fix this bitch-ass problem your auto code done broke you a little shit cuz you code sum dumb shit you dumbass it caint find no code.");
        //CommandScheduler.getInstance().enable();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autoCommand != null) {
            autoCommand.cancel();
        }

        robotContainer.startTeleop();
    }


    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        robotContainer.periodic();
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}