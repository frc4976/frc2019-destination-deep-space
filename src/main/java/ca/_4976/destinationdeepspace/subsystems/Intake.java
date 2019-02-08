package ca._4976.destinationdeepspace.subsystems;

import ca._4976.destinationdeepspace.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends PIDSubsystem {

    NetworkTable intake = NetworkTableInstance.getDefault().getTable("Intake");

    DoubleSolenoid hatchPanelPickUp = new DoubleSolenoid(6,7);
    boolean HP = true;
    TalonSRX intakeMover = new TalonSRX(11);
    TalonSRX intakeMoverSlave = new TalonSRX(12);
    TalonSRX intakeBall = new TalonSRX(13);
    AnalogInput cherrySensor = new AnalogInput(2);
    TalonSRX intakeMotor1 = new TalonSRX(14);
    TalonSRX intakeMotor2 = new TalonSRX(15);
    double intakeSetpoint;

    @Override
    protected void initDefaultCommand() {
        hatchPanelPickUp.set(DoubleSolenoid.Value.kForward);
        intakeMoverSlave.follow(intakeMover);
    }
    public void choose(){
        if(HP) Robot.intake.releaseGear();
        if(!HP)Robot.intake.holdGear();
    }
    public void pickUpBall(){
        intakeMotor1.set(ControlMode.PercentOutput, 0.5);
        intakeMotor2.set(ControlMode.PercentOutput, -0.5);
    }
    public void end(){
        intakeMotor1.set(ControlMode.PercentOutput, 0.0);
        intakeMotor2.set(ControlMode.PercentOutput, 0.0);
    }
    public void holdGear(){
        hatchPanelPickUp.set(DoubleSolenoid.Value.kForward);
    }
    public void releaseGear(){
        hatchPanelPickUp.set(DoubleSolenoid.Value.kReverse);
    }
    public void climb(){

    }
    public void disablePID(){intakeController.disable();}
    public void setSetpointHome(){intakeSetpoint = 172; intakeController.enable();}
    public void setSetpointHP(){intakeSetpoint = 83; intakeController.enable();}
    public void setSetpointClimb(){intakeSetpoint = 17; intakeController.enable();}
    public void moveIntake(double output){
        intakeMover.set(ControlMode.PercentOutput, output);
    }
    public PIDController intakeController;
    private final PIDOutput intakeOutput = this::usePIDOutput;
    public Intake(String name, double p, double i, double d){
        super(name, p, i, d);
        intakeController = new PIDController(p, i, d, cherrySensor, intakeOutput);
        intakeController.setSetpoint(intakeSetpoint);
    }
    @Override
    protected double returnPIDInput() {
        return cherrySensor.getVoltage()*36;
    }

    @Override
    protected void usePIDOutput(double output) {
        moveIntake(output);
    }
}