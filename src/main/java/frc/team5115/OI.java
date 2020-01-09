package frc.team5115;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team5115.Commands.*;
import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.Subsystems.Intake;

import static frc.team5115.Constants.*;


public class OI {
    public static Joystick joy = new Joystick(0);
    Button intake = new JoystickButton(joy, INTAKE_BUTTON_ID);

    public void OI(){
        intake.whenPressed(new IntakeBalls());
    }
}
