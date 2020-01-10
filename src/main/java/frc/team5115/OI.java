package frc.team5115;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.*;
import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.Subsystems.Intake;

import static frc.team5115.Constants.*;


public class OI {
    public static Joystick joy = new Joystick(0);
    Button intake;
    Button shot;

    public OI() {
        shot = new JoystickButton(joy, SHOOTER_BUTTON_ID);
        intake = new JoystickButton(joy, INTAKE_BUTTON_ID);

        intake.whenPressed(new IntakeBalls());
        shot.whenPressed(new Shoot());
    }
}
