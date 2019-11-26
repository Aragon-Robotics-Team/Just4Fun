package art840.just4fun;

public class RobotMap { // Drivetrain
    public static double maxSpeed = 0.8;
    public static double maxTicks = 7000;

    public static boolean coastMode = false;
    public static double rampingTime = 0.2;

    // FF
    public static double ffPercent = 0.8;
    public static double ffLeftVel = 5610;
    public static double ffRightVel = 5960;

    // Joystick
    public static double maxThrottle = 1.0;
    public static double maxRotation = 1.0;

    public static int portDriveTalonLeft = 2;
    public static int portDriveTalonRight = 3;
    public static int portDriveVictorLeft = 6;
    public static int portDriveVictorRight = 5;
}
