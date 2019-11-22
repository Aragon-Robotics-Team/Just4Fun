package art840.just4fun;

import art840.just4fun.command.TeleopMode;
import art840.just4fun.subsystems.Drivetrain;
import art840.just4fun.util.RobotUtil;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends RobotUtil {
    public static Drivetrain drivetrain = new Drivetrain();
    public static OI oi = new OI();

    public static CANSparkMax spark;
    
    public void teleopInit() {
        (new TeleopMode()).start();
    }

    public void robotInit() {
        spark = new CANSparkMax(3, MotorType.kBrushless);
        
        spark.restoreFactoryDefaults();
        spark.setMotorType(MotorType.kBrushless);
        spark.setOpenLoopRampRate(.1);
    }

    public void testPeriodic() {
        double x = oi.getYRight();
        
        x += oi.buttonB.get() ? 1.0 : 0.0;
        x += oi.buttonY.get() ? -1.0 : 0.0;
        
        x = Math.min(1.0, Math.max(-1.0, x));
        
        spark.set(x);
        
        // spark.set(oi.getYLeft());
    
    }
}
