package art840.just4fun.util;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class RobotUtil extends TimedRobot {
    public void robotInit() {}

    public void robotPeriodic() {
        Scheduler.getInstance().run();
    }

    public void disabledInit() {}

    public void disabledPeriodic() {}

    public void autonomousInit() {}

    public void autonomousPeriodic() {}

    public void teleopInit() {}

    public void teleopPeriodic() {}

    public void testInit() {}

    public void testPeriodic() {}
}
