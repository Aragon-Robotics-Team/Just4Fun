package art840.just4fun.command;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopMode extends CommandGroup {
    public TeleopMode() {
        addParallel(new Drive());
    }
}
