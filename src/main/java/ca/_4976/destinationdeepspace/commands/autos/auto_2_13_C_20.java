package ca._4976.destinationdeepspace.commands.autos;

import ca._4976.destinationdeepspace.commands.FireShooterRight;
import ca._4976.destinationdeepspace.commands.IsShootingHigh;
import ca._4976.destinationdeepspace.commands.autoModules.*;
import ca._4976.destinationdeepspace.commands.rpmRight;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class auto_2_13_C_20 extends CommandGroup {

    public auto_2_13_C_20(){
        addSequential(new DriveForwardsFromTwoToThirteen());
        addSequential(new Delay());
        addSequential(new HorizontalCenterShooter());
        addSequential(new Delay());
        addSequential(new IsShootingHigh());
        addSequential(new rpmRight());
        addSequential(new Delay());
        addSequential(new FireShooterRight());
        addSequential(new Delay());
        addSequential(new TurnFromThirteenToTwentyoneToGoBackwards());
        addSequential(new DriveBackwardsFromThirteenToTwentyone());
    }
}
