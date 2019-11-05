package art840.just4fun.command;

import art840.just4fun.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {
    public ArcadeDrive() {
        requires(Robot.drivetrain);
    }

    protected void execute() {
        double t = Robot.oi.getYRight();
        double r = Robot.oi.getXLeft();
        Robot.drivetrain.arcadeDrive(t, r);
    }

    protected boolean isFinished() {
        return false;
    }

}
