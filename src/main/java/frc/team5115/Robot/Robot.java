package frc.team5115.Robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team5115.Subsystems.Intake;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends TimedRobot {
  public static Intake intake;


  @Override
  public void robotInit() {
    intake = new Intake();

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
