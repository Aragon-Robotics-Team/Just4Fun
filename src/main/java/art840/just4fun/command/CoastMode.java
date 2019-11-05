package art840.just4fun.command;

import art840.just4fun.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class CoastMode extends Command {
    boolean coast;

    public CoastMode(boolean coast) {
        this.coast = coast;
    }

    protected void initialize() {
        Robot.drivetrain.setCoastMode(coast);
    }

    protected boolean isFinished() {
        return true;
    }
}
