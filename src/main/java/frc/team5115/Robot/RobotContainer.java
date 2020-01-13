package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Auto.AutoSeries;
import frc.team5115.Auto.StartingConfiguration;
import frc.team5115.Commands.IntakeBalls;
import frc.team5115.Subsystems.*;
import frc.team5115.Subsystems.Locationator;

import static frc.team5115.Constants.INTAKE_BUTTON_ID;
import static frc.team5115.Constants.SHOOTER_BUTTON_ID;

public class RobotContainer {

    //Remember to update. todome update this every time.
    final StartingConfiguration startingConfiguration = StartingConfiguration.Left;
    public static final double startY = 20;
    public static final int startingAngle = 90; //90 is looking away from the driver stations.
    public static final double MAX_AUTO_THROTTLE = 0.5;
    public static final double CAMERA_HEIGHT = 10; //units: inches. todome update
    public static final double CAMERA_ANGLE = 10; //units: degrees. todome update
    public static final double SHOOTING_DISTANCE = 120; //units: inches. todome update
    public static final double HIGH_GOAL_HEIGHT = 98.25; //units: inches. todome update


    // The robot's subsystems and commands are defined here...
    //Subsystems
    private final Drivetrain drivetrain = new Drivetrain(this);
    public final Locationator locationator = new Locationator(drivetrain, startingConfiguration, startingAngle);
    public final Limelight limelight = new Limelight();
    public final Shooter shooter = new Shooter();
    public final Intake intake = new Intake();
    public static Joystick joy = new Joystick(0);
    //commands
    private final AutoSeries autoSeries = new AutoSeries(drivetrain, locationator, shooter, limelight);


    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        //sets the navx to work.
    }

    private Button intake_Button = new JoystickButton(joy, INTAKE_BUTTON_ID);
    private Button shooter_Button = new JoystickButton(joy, SHOOTER_BUTTON_ID);

    private void configureButtonBindings() {
            intake_Button.whenPressed(new IntakeBalls(intake));
            shooter_Button.whenPressed(new InstantCommand(shooter::shoot));

            new RunCommand(() -> drivetrain.drive(
                joy.getRawAxis()
                driverController.getX(GenericHID.Hand.kRight)),
                m_robotDrive).schedule();
    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return autoSeries;
    }
}