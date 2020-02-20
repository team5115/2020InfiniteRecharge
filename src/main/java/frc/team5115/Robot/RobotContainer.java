package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Auto.AutoCommands.PickupBallAuto;
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

        new JoystickButton(joy, AUTO_TURN_AND_MOVE_BUTTON_ID).whenHeld(new ShootHighGoal(drivetrain, locationator, shooter, limelight));
        new JoystickButton(joy, AUTO_TURN_BUTTON_ID).whenHeld(new AssistedShootHighGoal(drivetrain, shooter, limelight, joy));
        new JoystickButton(joy, AUTO_BALL_TARCKING).whenHeld(new PickupBallAuto(drivetrain, locationator, limelight, feeder));
        new JoystickButton(joy, SHOOTER_BUTTON_ID).whenHeld(new InstantCommand(shooter::shoot)).whenReleased(new InstantCommand(shooter::stopShoot));
        new JoystickButton(joy, CLIMBER_UP_BUTTON_ID).whenHeld(new InstantCommand(climber::ScissorUp)).whenReleased(new InstantCommand(climber::StopClimb));
//        new JoystickButton(joy, CLIMBER_DOWN_BUTTON_ID).whenHeld(
//                new InstantCommand(climber::ScissorDown)
//                        .alongWith(new InstantCommand(climber::WinchDown)))
//                .whenReleased(new InstantCommand(climber::StopClimb));
        new JoystickButton(joy, SCISSORS_DOWN_BUTTON_ID).whenHeld(new InstantCommand(climber::ScissorDown));
        new JoystickButton(joy, WINCH_DOWN_BUTTON_ID).whenHeld(new InstantCommand(climber::WinchDown));
        new JoystickButton(joy, WINCH_RELEASE_BUTTON_ID).whenHeld(new InstantCommand(climber::WinchRelease));

        new JoystickButton(joy, INTAKE_BUTTON_ID)
                .whenHeld(
                        new InstantCommand(intake::driverIntake)
                                .alongWith(new InstantCommand(feeder::moveCells)))
                .whenReleased(
                        new InstantCommand(intake::stopIntake)
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
            //drivetrain.drive(-.5, -.5, 1);
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
        System.out.println("Starting teleop");
    }
}