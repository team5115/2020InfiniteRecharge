package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.*;
import frc.team5115.Subsystems.*;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

import static frc.team5115.Constants.*;

public class RobotContainer implements Loggable {
    public static Joystick joy = new Joystick(0);
    public JoystickButton intakeButton = new JoystickButton(joy, INTAKE_BUTTON_ID);
    public JoystickButton shotButton = new JoystickButton(joy, SHOOTER_BUTTON_ID);
    public JoystickButton climbUpButton = new JoystickButton(joy,CLIMB_UP_BUTTON_ID);
    public JoystickButton climbDownButton = new JoystickButton(joy, ClIMB_DOWN_BUTTON_ID);

    public final static Shooter shooter = new Shooter();
    public final static Intake intake = new Intake();
    public final static Feeder feeder = new Feeder();
    public final static Climber climber = new Climber();

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        intakeButton.whenPressed(new DriverIntake());
        shotButton.whenPressed(new Shoot());
        climbUpButton.whenPressed(new ClimbUp());
    }
//new InstantCommand(shooter::exhale)
    public Command getAutonomousCommand() {
        return null;
    }
}