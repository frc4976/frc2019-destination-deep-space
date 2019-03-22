package ca._4976.destinationdeepspace;

import ca._4976.destinationdeepspace.commands.autoModules.DriveForwardsFromGroundToLeftSide;
import ca._4976.destinationdeepspace.subsystems.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Robot extends TimedRobot {

    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }

    public static OI oi;
    public static Drive drive;
    public static Shooter shooter;
    public static Intake intake;
    public static Climber climber;
    public static Vision vision;
    public static Compressor compressor;
    public static CompressorSwitch compressorSwitch;

    private Scheduler scheduler;

    @Override
    public void robotInit() {
        oi = new OI();
        drive = new Drive();
        shooter = new Shooter();
        intake = new Intake();
        climber = new Climber();
        vision = new Vision();
        compressorSwitch = new CompressorSwitch();
        compressor = new Compressor(40);

        Robot.vision.viewCamera();

        Robot.drive.gearShift.set(true);

        scheduler = Scheduler.getInstance();

        Robot.intake.resetIntakeEncoder();

        Shuffleboard.getTab("Climber").add(climber);

        Robot.drive.LF.setInverted(true);
        Robot.drive.RF.setInverted(true);
        Robot.drive.LB.setInverted(true);
        Robot.drive.RB.setInverted(true);

        Robot.intake.intakeArm.setSelectedSensorPosition(0);
    }

    @Override
    public void robotPeriodic(){
        scheduler.run();
//        System.out.println("Climber encoder val:" + Robot.climber.climberLeg.getSelectedSensorPosition());
//        System.out.println("Shooter speed val:" + Robot.vision.distance);
        System.out.println("Intake encoder: " + Robot.intake.intakeArm.getSelectedSensorPosition());
        //controls climber leg by operator triggers (probably should be moved to climber class)

        if (Robot.oi.operator.getRawAxis(5) > 0.1 || Robot.oi.operator.getRawAxis(5) < -0.1){
            Robot.climber.climberLeg.set(ControlMode.PercentOutput, -Robot.drive.applyDeadband(Robot.oi.operator.getRawAxis(5)));
        }

    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() {
        Robot.climber.climberLeg.setSelectedSensorPosition(0);
    }

    @Override
    public void testInit() { }

    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousPeriodic() {   vision.periodicRead();
        //Dpad sensor for operator controller
        if (Robot.oi.operator.getPOV() == 0) {
            // Calls the move camera forwards method
            Robot.vision.cameraForwards();
        }
        else if (Robot.oi.operator.getPOV() == 90) {
            // Calls the move camera right method
            Robot.vision.cameraRight();
        }
        else if (Robot.oi.operator.getPOV() == 180) {
            Robot.shooter.areYouShootingHigh();
        }
        else if (Robot.oi.operator.getPOV() == 270) {
            // Calls the move camera left method
            Robot.vision.cameraLeft();
        }

        //Dpad sensor for driver controller
        if (Robot.oi.driver.getPOV() == 0) {
        }
        else if (Robot.oi.driver.getPOV() == 90) {
        }
        else if (Robot.oi.driver.getPOV() == 180) {
        }
        else if (Robot.oi.driver.getPOV() == 270) {
        }
    }

    @Override
    public void teleopPeriodic() {
        System.out.println("Intake: "+Robot.intake.intakeArm.getSelectedSensorPosition());

        vision.periodicRead();
        //Dpad sensor for operator controller
        if (Robot.oi.operator.getPOV() == 0) {
            // Calls the move camera forwards method
            Robot.vision.cameraForwards();
        }
        else if (Robot.oi.operator.getPOV() == 90) {
            // Calls the move camera right method
            Robot.vision.cameraRight();
        }
        else if (Robot.oi.operator.getPOV() == 180) {
            Robot.shooter.areYouShootingHigh();
        }
        else if (Robot.oi.operator.getPOV() == 270) {
            // Calls the move camera left method
            Robot.vision.cameraLeft();
        }

        //Dpad sensor for driver controller
        if (Robot.oi.driver.getPOV() == 0) {
        }
        else if (Robot.oi.driver.getPOV() == 90) {
        }
        else if (Robot.oi.driver.getPOV() == 180) {
        }
        else if (Robot.oi.driver.getPOV() == 270) {
        }
    }
    @Override
    public void testPeriodic() { }
}