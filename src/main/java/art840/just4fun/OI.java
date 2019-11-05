package art840.just4fun;

import art840.just4fun.command.CoastMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    Joystick joystick = new Joystick(0);

    JoystickButton buttonB = new JoystickButton(joystick, 2);
    JoystickButton buttonY = new JoystickButton(joystick, 4);

    public OI() {
        buttonB.whenPressed(new CoastMode(false));
        buttonY.whenPressed(new CoastMode(true));
    }

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
