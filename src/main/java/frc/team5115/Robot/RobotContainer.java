package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Auto.AutoSeries;
import frc.team5115.Auto.AutoCommands.AssistedShootHighGoal;
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
    public final Climber climber = new Climber();
    public final Feeder feeder = new Feeder();

    public final Joystick joy = new Joystick(0);

    private final AutoSeries autoSeries;

    public RobotContainer() {
        // Configure the button bindings
        //sets the navx to work.
        locationator = new Locationator(this, startingConfiguration, startingAngle);
        drivetrain = new Drivetrain(this);
        autoSeries = new AutoSeries(drivetrain, locationator, shooter, limelight);
        configureButtonBindings();
    }

    private void configureButtonBindings() {

        new JoystickButton(joy, AUTO_TURN_AND_MOVE_BUTTON_ID)
                .whenHeld(new ShootHighGoal(drivetrain, locationator, shooter, limelight));

        new JoystickButton(joy, AUTO_TURN_BUTTON_ID)
                .whenHeld(new AssistedShootHighGoal(drivetrain, shooter, limelight, joy));

        //new JoystickButton(joy, AUTO_BALL_TRACKING).whenHeld(new PickupBallAuto(drivetrain, locationator, limelight, feeder));

        //new JoystickButton(joy, GRANNY_MODE_BUTTON_ID).whenPressed(new InstantCommand(drivetrain::toggleSpeed));

        new POVButton(joy, WINCH_DOWN_BUTTON_ANGLE)
                .whenHeld(new InstantCommand(climber::WinchDown))
                .whenReleased(new InstantCommand(climber::StopClimb));

        new POVButton(joy, WINCH_UP_BUTTON_ANGLE)
                .whenHeld(new InstantCommand(climber::WinchUp))
                .whenReleased(new InstantCommand(climber::StopClimb));

        new JoystickButton(joy, SHOOTER_BUTTON_ID)
                .whenHeld(new InstantCommand(shooter::shoot)
                    .alongWith(new InstantCommand(feeder::moveCells)))
                .whenReleased(new InstantCommand(shooter::stopShoot)
                    .alongWith(new InstantCommand(feeder::stopCells)));

        new JoystickButton(joy, CLIMBER_UP_BUTTON_ID)
                .whenHeld(new InstantCommand(climber::ScissorUp)
                    .alongWith(new InstantCommand(climber::print)))
                .whenReleased(new InstantCommand(climber::StopClimb));

        new JoystickButton(joy, CLIMBER_DOWN_BUTTON_ID)
                .whenHeld(new InstantCommand(climber::ScissorDown)
                    .alongWith(new InstantCommand(climber::print)))
                .whenReleased(new InstantCommand(climber::StopClimb));

        new JoystickButton(joy, INTAKE_EXCRETE_BUTTON_ID)
                .whenHeld(new InstantCommand(intake::spitout))
                .whenReleased(new InstantCommand(intake::stopIntake));

        new JoystickButton(joy, INTAKE_BUTTON_ID)
                .whenHeld(new InstantCommand(intake::driverIntake)
                    .alongWith(new InstantCommand(feeder::moveCells)))
                .whenReleased(new InstantCommand(intake::stopIntake)
                    .alongWith(new InstantCommand(feeder::stopCells)));

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
            drivetrain.drive(joystick.getRawAxis(XBOX_X_AXIS_ID), joystick.getRawAxis(XBOX_Y_AXIS_ID), 1);
            //drivetrain.XBoxDrive(joystick);
        }
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

    public void startTeleop() {
        //bind the wheels.
        System.out.println("Starting teleop: " + VERSION);
    }
}