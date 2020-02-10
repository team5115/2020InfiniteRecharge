package frc.team5115.Commands.Shooter;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Drivetrain;
import frc.team5115.Subsystems.Limelight;
import frc.team5115.Subsystems.Locationator;
import frc.team5115.Subsystems.Shooter;

import static frc.team5115.Constants.JOYSTICK_Y_AXIS_ID;
import static frc.team5115.Constants.Pipeline;


public class AssistedShootHighGoal extends CommandBase {

    Drivetrain drivetrain;
    Locationator locationator;
    Shooter shooter;
    Limelight limelight;
    Joystick joystick;

    /*
    1. Aim at the thing using the limelight
    2. Shoot the balls while stopped.
     */

    public AssistedShootHighGoal(Drivetrain drivetrain, Shooter shooter, Limelight limelight, Joystick joystick) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.limelight = limelight;
        this.joystick = joystick;
        addRequirements(drivetrain, shooter);
        limelight.setPipeline(Pipeline.GreenLedMode);
        System.out.println("Starting goal assist but not throttle constructor");

    }

    @Override
    public void initialize() {
        System.out.println("Starting High Goal Aiming but not throttle");
    }

    @Override
    public void execute() {
        double angle = 127;
        if (limelight.hasTarget() && limelight.getYAngle() > 0) { // if we don't have a target
            angle = limelight.getXAngle();
        } else {
            System.out.println("Error No Target Found"); //todome set to shuffleboard.
            drivetrain.XBoxDrive(joystick);
            return;
        }
        //System.out.println("angle = " + (angle - locationator.getAngle()));
        drivetrain.relativeAngleHold(angle, -joystick.getRawAxis(JOYSTICK_Y_AXIS_ID));
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) System.out.println("Error: Interrupted");
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false; //never needed, command ends itself.
    }
}
