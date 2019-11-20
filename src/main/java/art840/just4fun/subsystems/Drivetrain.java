package art840.just4fun.subsystems;

import art840.just4fun.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
    TalonSRX talonL = new TalonSRX(RobotMap.portDriveTalonLeft);
    TalonSRX talonR = new TalonSRX(RobotMap.portDriveTalonRight);

    VictorSPX victorL = new VictorSPX(RobotMap.portDriveVictorLeft);
    VictorSPX victorR = new VictorSPX(RobotMap.portDriveVictorRight);

    public Drivetrain() {
        victorL.follow(talonL);
        victorR.follow(talonR);

        setCoastMode(RobotMap.coastMode);
        setRamping(RobotMap.rampingTime);

        talonR.setInverted(InvertType.InvertMotorOutput);
        victorR.setInverted(InvertType.FollowMaster);
    }

    public void drive(double speedl, double speedr) {
        speedl *= RobotMap.maxSpeed;
        speedr *= RobotMap.maxSpeed;

        talonL.set(ControlMode.PercentOutput, speedl);
        talonR.set(ControlMode.PercentOutput, speedr);
    }

    public void drive(double speedr) {
        drive(speedr, speedr);
    }

    public void arcadeDrive(double throttle, double rotation) {
        throttle *= RobotMap.maxThrottle;
        rotation *= RobotMap.maxRotation;

        double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(rotation)), throttle);

        if (throttle * rotation >= 0.0) {
            drive(maxInput, throttle - rotation);
        } else {
            drive(throttle + rotation, maxInput);
        }
    }

    public void setCoastMode(boolean coast) {
        NeutralMode coastMode = coast ? NeutralMode.Coast : NeutralMode.Brake;

        talonL.setNeutralMode(coastMode);
        talonR.setNeutralMode(coastMode);
        victorL.setNeutralMode(coastMode);
        victorR.setNeutralMode(coastMode);
    }

    public void setRamping(double rampingTime) {
        talonR.configOpenloopRamp(rampingTime);
        talonL.configOpenloopRamp(rampingTime);
    }

    protected void initDefaultCommand() {}
}
