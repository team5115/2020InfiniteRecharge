package frc.team5115.Configuration.Wrappers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

public class AnalogButton extends Button {

    private GenericHID joystick;
    int axis;
    double bound = 0;

    public AnalogButton(GenericHID joystick, int axis, double bound){
        super();
        this.joystick = joystick;
        this.axis = axis;
        this.bound = bound;
    }

    public AnalogButton(GenericHID joystick, int axis){
        this(joystick, axis, 0.5);
    }

    @Override
    public boolean get(){
        return Math.abs(joystick.getRawAxis(axis)) >= bound;
    }
}
