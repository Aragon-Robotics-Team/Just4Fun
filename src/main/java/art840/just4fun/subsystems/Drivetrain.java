package art840.just4fun.subsystems;

import art840.just4fun.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {
    TalonSRX talonL = new TalonSRX(RobotMap.portDriveTalonLeft);
    TalonSRX talonR = new TalonSRX(RobotMap.portDriveTalonRight);

    VictorSPX victorL = new VictorSPX(RobotMap.portDriveVictorLeft);
    VictorSPX victorR = new VictorSPX(RobotMap.portDriveVictorRight);

    Faults lFault = new Faults();
    Faults rFault = new Faults();

    int n = 0;

    public Drivetrain() {
        configController(talonL);
        configController(talonR);
        configController(victorL);
        configController(victorR);

        victorL.follow(talonL);
        victorR.follow(talonR);

        setCoastMode(RobotMap.coastMode);
        setRamping(RobotMap.rampingTime);

        talonR.setInverted(InvertType.InvertMotorOutput);
        victorR.setInverted(InvertType.FollowMaster);

        configEncoder(talonL);
        configEncoder(talonR);
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
        throttle = Math.copySign(throttle * throttle, throttle);
        rotation = Math.copySign(rotation * rotation, rotation);

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

    void configController(BaseMotorController talon) {
        talon.clearStickyFaults();
        talon.configFactoryDefault();
    }

    void configEncoder(TalonSRX talon) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        talon.setSensorPhase(false);
    }

    public void periodic() {
        SmartDashboard.putNumber("Left Velocity", talonL.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Left Position", talonL.getSelectedSensorPosition());

        SmartDashboard.putNumber("Right Velocity", talonR.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right Position", talonR.getSelectedSensorPosition());

        n++;

        if (n < 50) {
            return;
        }

        n = 0;

        talonL.getFaults(lFault);
        talonR.getFaults(rFault);

        System.out.println("Left: " + lFault + "\n");
        System.out.println("Right: " + rFault + "\n");

        SmartDashboard.putBoolean("Left Phase Bad", lFault.SensorOutOfPhase);
        SmartDashboard.putBoolean("Right Phase Bad", rFault.SensorOutOfPhase);
    }

    protected void initDefaultCommand() {}
}
