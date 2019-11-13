package art840.just4fun.command;

import art840.just4fun.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Auto1 extends Command {
    public Auto1() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        setTimeout(2);
    }

    protected void execute() {
        Robot.drivetrain.drive(1.0);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }
}
