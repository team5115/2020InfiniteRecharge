package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Auto.AutoSeries;
import frc.team5115.Commands.IntakeBalls;
import frc.team5115.Subsystems.*;

import static frc.team5115.Constants.*;

public class RobotContainer {

    // The robot's subsystems and commands are defined here...
    //Subsystems
    public Drivetrain drivetrain;
    public Locationator locationator;
    public final Limelight limelight = new Limelight();
    public final Shooter shooter = new Shooter();
    public final Intake intake = new Intake();
    public static Joystick joy = new Joystick(0);
    //commands
    private final AutoSeries autoSeries;


    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        //sets the navx to work.
        System.out.println("Starting creation of autoseries");
        locationator = new Locationator(this, startingConfiguration, startingAngle);
        drivetrain = new Drivetrain(this);
        autoSeries = new AutoSeries(drivetrain, locationator, shooter, limelight);

    }

    private Button intake_Button = new JoystickButton(joy, INTAKE_BUTTON_ID);
    private Button shooter_Button = new JoystickButton(joy, SHOOTER_BUTTON_ID);

    private void configureButtonBindings() {
        intake_Button.whenPressed(new IntakeBalls(intake));
        shooter_Button.whenPressed(new InstantCommand(shooter::Inhale));
        new JoystickButton(joy, RESET_BUTTON).whenPressed(new InstantCommand(()-> {
            locationator.setAngleAndLocation(90, startingConfiguration.getX(), 30);
//            System.out.println("Button Pressed");
        }));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        locationator.setAngleAndLocation(90, startingConfiguration.getX(), 30);
        return autoSeries;
    }

    public void startTeleop() {
        //bind the wheels.
        System.out.println("Starting teleop");
        new RunCommand(() -> {
            drivetrain.drive(
                    joy.getRawAxis(X_AXIS_ID)/2,
                    -joy.getRawAxis(Y_AXIS_ID), //note: negative because pushing forward is a negative value on the joystick.
                    0.6);//joy.getRawAxis(THROTTLE_AXIS_ID));
        }).schedule();
    }
}