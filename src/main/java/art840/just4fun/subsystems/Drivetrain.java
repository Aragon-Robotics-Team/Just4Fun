package art840.just4fun.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import art840.just4fun.RobotMap;

public class Drivetrain extends Subsystem {
    TalonSRX talonL = new TalonSRX(RobotMap.portDriveTalonLeft);
    TalonSRX talonR = new TalonSRX(RobotMap.portDriveTalonRight);

    VictorSPX victorL = new VictorSPX(RobotMap.portDriveVictorLeft);
    VictorSPX victorR = new VictorSPX(RobotMap.portDriveVictorRight);

    public Drivetrain() {
        victorL.follow(talonL);
        victorR.follow(talonR);

        boolean coast = true;
        NeutralMode coastMode = coast ? NeutralMode.Coast : NeutralMode.Brake;

        talonL.setNeutralMode(coastMode);
        talonR.setNeutralMode(coastMode);
        victorL.setNeutralMode(coastMode);
        victorR.setNeutralMode(coastMode);
    }

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
