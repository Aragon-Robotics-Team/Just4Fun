package art840.just4fun.command;

import art840.just4fun.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class VoltageCompensationMode extends Command {
    double voltage;

    public VoltageCompensationMode(double voltage) {
        this.voltage = voltage;
    }

    protected void initialized() {
        Robot.drivetrain.setVoltageCompensation(voltage);
    }

    protected boolean isFinished() {
        return true;
    }
}
