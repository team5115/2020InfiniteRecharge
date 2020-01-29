package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Auto.AutoSeries;
import frc.team5115.Commands.AssistedShootHighGoal;
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
        //sets the navx to work.
        locationator = new Locationator(this, startingConfiguration, startingAngle);
        drivetrain = new Drivetrain(this);
        autoSeries = new AutoSeries(drivetrain, locationator, shooter, limelight);
        configureButtonBindings();
    }

    private Button intake_Button = new JoystickButton(joy, INTAKE_BUTTON_ID);
    private Button shooter_Button = new JoystickButton(joy, SHOOTER_BUTTON_ID);

    private void configureButtonBindings() {
        intake_Button.whenPressed(new IntakeBalls(intake));
        shooter_Button.whenPressed(new InstantCommand(shooter::Inhale));
        new JoystickButton(joy, RESET_BUTTON).whenPressed(new InstantCommand(() -> {
            locationator.setAngleAndLocation(90, startingConfiguration.getX(), 30);
//            System.out.println("Button Pressed");
        }));

        new JoystickButton(joy, AUTO_LINEUP_BUTTON_ID).whenHeld(new ShootHighGoal(drivetrain, locationator, shooter, limelight));
        new JoystickButton(joy, AUTO_TURN_ASSIST_BUTTON_ID).whenHeld(new AssistedShootHighGoal(drivetrain, shooter, limelight, joy));

        drivetrain.setDefaultCommand(new driveDefaultCommand(drivetrain, joy).perpetually());

    }

    static class driveDefaultCommand extends CommandBase {

        Drivetrain drivetrain;
        Joystick joystick;

        public driveDefaultCommand(Drivetrain drivetrain, Joystick joystick) {
            addRequirements(drivetrain);
            this.drivetrain = drivetrain;
            this.joystick = joystick;
        }

        @Override
        public void execute() {
            drivetrain.drive(
                    joy.getRawAxis(X_AXIS_ID) / 2,
                    -joy.getRawAxis(Y_AXIS_ID), //note: negative because pushing forward is a negative value on the joystick.
                    KID_MODE ? KID_MODE_MAX_SPEED : NORMAL_MODE_MAX_SPEED);//joy.getRawAxis(THROTTLE_AXIS_ID));
            //locationator.printValues();
        }
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
    }
}