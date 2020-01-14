package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;

import static frc.team5115.Constants.*;

public class Shooter extends SubsystemBase {
    TalonSRX shooter;
    double intakeSpeed = 0.5;

    public Shooter(){
        shooter = new TalonSRX(SHOOTER_MOTOR_ID);
    }

    public void exhale(){
        shooter.set(ControlMode.PercentOutput, intakeSpeed);
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static class Shoot extends CommandBase {
        @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

        public Shoot() {
            // Use addRequirements() here to declare subsystem dependencies.
            addRequirements(RobotContainer.shooter);

        }

        @Override
        public void initialize() {
            RobotContainer.shooter.exhale();
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
}

