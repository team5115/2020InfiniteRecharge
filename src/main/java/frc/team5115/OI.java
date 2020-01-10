package frc.team5115;


import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.*;
import edu.wpi.first.wpilibj.Joystick;
import frc.team5115.Subsystems.Intake;

import static frc.team5115.Constants.*;


public class OI {
    @Deprecated
    public static Joystick joy = new Joystick(0);

    @Deprecated
    Button intake = new JoystickButton(joy, INTAKE_BUTTON_ID);
    @Deprecated
    Button shot = new JoystickButton(joy, SHOOTER_BUTTON_ID);


    @Deprecated
    public void OI(){
        intake.whenPressed(new IntakeBalls());
        shot.whenPressed(new Shoot());
    }
}
