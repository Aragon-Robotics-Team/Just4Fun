package art840.just4fun.subsystems;

import art840.just4fun.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
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

        // var config = new TalonSRXConfiguration();

        // config.slot0.kP = 0.2;
        // config.slot0.kI = 1.0;
        // config.slot0.integralZone = 100;
        // config.voltageCompSaturation = 11;
        // config.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

        // enable vcomp
        // invert
        // follow
        // encoder phase
        // reset encoder positon

        // talonL.configAllSettings(allConfigs)

        configController(talonL);
        configController(talonR);
        configController(victorL);
        configController(victorR);
        
        setVoltageCompensation(11);

        victorL.follow(talonL);
        victorR.follow(talonR);

        setCoastMode(RobotMap.coastMode);
        setRamping(RobotMap.rampingTime);

        talonR.setInverted(InvertType.InvertMotorOutput);
        victorR.setInverted(InvertType.FollowMaster);

        configEncoder(talonL);
        configEncoder(talonR);

        talonL.config_kF(0, calcFF(RobotMap.ffLeftVel));
        talonR.config_kF(0, calcFF(RobotMap.ffRightVel));

        drive(0);
    }

    public void drive(double speedl, double speedr) {
        speedl *= RobotMap.maxSpeed;
        speedr *= RobotMap.maxSpeed;

        // talonL.set(ControlMode.PercentOutput, speedl);
        // talonR.set(ControlMode.PercentOutput, speedr);

        speedl *= RobotMap.maxTicks;
        speedr *= RobotMap.maxTicks;

        talonL.set(ControlMode.Velocity, (int)(speedl), DemandType.ArbitraryFeedForward, calc_A_FF(speedl));
        talonR.set(ControlMode.Velocity, (int)(speedr), DemandType.ArbitraryFeedForward, calc_A_FF(speedr));
    }
    
    double calc_A_FF(double speed) {
        double FF = 0.00;
        
        if (Math.abs(speed) < 2) {
            return 0;
        } else if (speed > 0) {
            return FF;
        } else {
            return -1 * FF;
        }
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
        
        talonR.configClosedloopRamp(rampingTime);
        talonL.configClosedloopRamp(rampingTime);
    }

    void configController(BaseMotorController talon) {
        talon.clearStickyFaults();
        talon.configFactoryDefault();
    }

    void configEncoder(TalonSRX talon) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        talon.setSensorPhase(true);
        talon.setSelectedSensorPosition(0);
        
        talon.config_kP(0, .2);
        talon.config_IntegralZone(0, 300);
        talon.config_kI(0, .001);
    }

    double calcFF(double val) {
        return (RobotMap.ffPercent * 1023) / val;
    }

    public void periodic() {
        SmartDashboard.putNumber("Left Velocity", talonL.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Left Position", talonL.getSelectedSensorPosition());
        SmartDashboard.putNumber("Left Error", talonL.getClosedLoopError());
        SmartDashboard.putNumber("Left Target", talonL.getClosedLoopTarget());
        SmartDashboard.putNumber("Left Output", talonL.getMotorOutputPercent());

        SmartDashboard.putNumber("Right Velocity", talonR.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right Position", talonR.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Error", talonR.getClosedLoopError());
        SmartDashboard.putNumber("Right Target", talonR.getClosedLoopTarget());
        SmartDashboard.putNumber("Right Output", talonR.getMotorOutputPercent());

        n++;

        if (n < 50) {
            return;
        }

        n = 0;

        talonL.getFaults(lFault);
        talonR.getFaults(rFault);

        // System.out.println("Left: " + lFault + "\n");
        // System.out.println("Right: " + rFault + "\n");

        // SmartDashboard.putBoolean("Left Phase Bad", lFault.SensorOutOfPhase);
        // SmartDashboard.putBoolean("Right Phase Bad", rFault.SensorOutOfPhase);
    }
    
    public void setVoltageCompensation(double voltage) {
        talonR.configVoltageCompSaturation(voltage);
        talonR.enableVoltageCompensation(true);
        
        talonL.configVoltageCompSaturation(voltage);
        talonL.enableVoltageCompensation(true);
    }

    protected void initDefaultCommand() {}
}
