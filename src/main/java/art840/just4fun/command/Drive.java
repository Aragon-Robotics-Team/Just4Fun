package art840.just4fun.command;

import art840.just4fun.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
    public Drive() {
        requires(Robot.drivetrain);
    }

    protected void execute() {
        double yLeft = Robot.oi.getYLeft();
        double yRight = Robot.oi.getYRight();
        Robot.drivetrain.drive(yLeft, yRight);
    }

    protected boolean isFinished() {
        return false;
    }
}
