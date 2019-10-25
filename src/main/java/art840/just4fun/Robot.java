package art840.just4fun;

import art840.just4fun.command.TeleopMode;
import art840.just4fun.util.RobotUtil;

public class Robot extends RobotUtil {
    public void teleopInit() {
        (new TeleopMode()).start();
    }
}
