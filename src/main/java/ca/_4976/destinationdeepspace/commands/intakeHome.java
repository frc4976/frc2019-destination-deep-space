package ca._4976.destinationdeepspace.commands;

import ca._4976.destinationdeepspace.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class intakeHome extends Command {
    @Override
    protected void initialize(){
        Robot.intake.setSetpointHome();
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void end(){
        Robot.intake.disablePID();
    }
}