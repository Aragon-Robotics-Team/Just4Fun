package art840.just4fun;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    Joystick joystick = new Joystick(0);

    // logitech f310
    public double getXLeft() {
        return joystick.getRawAxis(0);
    }

    public double getYLeft() {
        return joystick.getRawAxis(1) * -1;
    }

    public double getXRight() {
        return joystick.getRawAxis(4);
    }

    public double getYRight() {
        return joystick.getRawAxis(5) * -1;
    }
}
