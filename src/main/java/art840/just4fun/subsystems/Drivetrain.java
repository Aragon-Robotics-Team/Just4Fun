package art840.just4fun.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import art840.just4fun.RobotMap;

public class Drivetrain extends Subsystem {
    TalonSRX talonL = new TalonSRX(RobotMap.portDriveTalonLeft);
    TalonSRX talonR = new TalonSRX(RobotMap.portDriveTalonRight);

    public void drive(double speedl, double speedr) {
        talonL.set(ControlMode.PercentOutput, speedl);
        talonR.set(ControlMode.PercentOutput, speedr);
    }
    
    public void drive(double speedr) {
        drive(speedr, speedr);
    }

    public void arcadeDrive(double throttle, double rotation) {
        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotation)), throttle);

        if (throttle * rotation >= 0.0) {
			drive(maxInput, throttle - rotation);
		} else {
			drive(throttle + rotation, maxInput);
		}
    } 

    protected void initDefaultCommand() {
    }
}
