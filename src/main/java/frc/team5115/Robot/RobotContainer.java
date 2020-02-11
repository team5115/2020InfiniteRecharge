package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Auto.AutoCommands.AssistedShootHighGoal;
import frc.team5115.Auto.AutoCommands.PickupBallAuto;
import frc.team5115.Auto.AutoCommands.ShootHighGoal;
import frc.team5115.Auto.AutoSeries;
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

    //commands
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
        new JoystickButton(joy, RESET_BUTTON).whenPressed(new InstantCommand(() -> {
            locationator.setAngleAndLocation(90, startingConfiguration.getX(), 30);
//            System.out.println("Button Pressed");
        }));
        //Simple lineup
        new JoystickButton(joy, AUTO_LINEUP_BUTTON_ID).whenHeld(new ShootHighGoal(drivetrain, locationator, shooter, limelight));
        //just angle
        new JoystickButton(joy, AUTO_TURN_ASSIST_BUTTON_ID).whenHeld(new AssistedShootHighGoal(drivetrain, shooter, limelight, joy));
        //ball lineup
        new JoystickButton(joy, AUTO_GET_BALL_BUTTON).whenHeld(new PickupBallAuto(drivetrain, locationator, limelight, feeder));

        //shooter button - shoot shooter and flush the feeder
        new JoystickButton(joy, SHOOTER_BUTTON_ID)
                .whenHeld(
                        new RunCommand(shooter::shoot).alongWith(new RunCommand(feeder::flush)));
        //Climbing up
        new JoystickButton(joy, CLIMB_UP_BUTTON_ID).whenHeld(
                new RunCommand(climber::ScissorUp));
        //winching and lowering stuff.
        new JoystickButton(joy, ClIMB_DOWN_BUTTON_ID).whenHeld(
                new RunCommand(climber::ScissorDown).alongWith(new RunCommand(climber::WinchDown)));
        //Spitting balls out of intake.
        new JoystickButton(joy, INTAKE_DEJAM_BUTTON_ID)
                .whenHeld(
                        new InstantCommand(intake::spitout));
        //Spitting balls out of the feeder
        new JoystickButton(joy, FEEDER_DEJAM_BUTTON_ID).
                whenHeld(
                        new InstantCommand(feeder::spit));

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
            if(USING_XBOX) {
                drivetrain.XBoxDrive(joystick);
            } else {
                drivetrain.drive(
                        joystick.getRawAxis(JOYSTICK_X_AXIS_ID) / 2,
                        -joystick.getRawAxis(JOYSTICK_Y_AXIS_ID), //note: negative because pushing forward is a negative value on the joystick.
                        KID_MODE ? KID_MODE_MAX_SPEED : NORMAL_MODE_MAX_SPEED);//joy.getRawAxis(THROTTLE_AXIS_ID));
                //locationator.printValues();
                //drivetrain.printAllEncoders();
            }
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