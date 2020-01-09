package frc.team5115.Robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team5115.Subsystems.*;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {
  public static Intake intake;
  public static Shooter shooter;


  @Override
  public void robotInit() {
    intake = new Intake();
    shooter = new Shooter();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
