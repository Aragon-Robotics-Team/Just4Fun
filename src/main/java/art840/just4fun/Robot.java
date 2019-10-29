package art840.just4fun;

import art840.just4fun.command.TeleopMode;
import art840.just4fun.subsystems.Drivetrain;
import art840.just4fun.util.RobotUtil;

public class Robot extends RobotUtil {
    public static Drivetrain drivetrain = new Drivetrain();
    public static OI oi = new OI();

    public void teleopInit() {
        (new TeleopMode()).start();
    }
}
