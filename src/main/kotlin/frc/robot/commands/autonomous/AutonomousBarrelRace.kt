package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI

class AutonomousBarrelRace : SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    // TODO: Remember to INVERT WITH CHASSISDRIVEAMT

    init {

        val percentOneFtPerSec = 0.2
        val percentRunningAt = 0.5
        val radPerSec = 0.6

        addCommands(ChassisDriveAmt(0.0, -0.8, 1.633),
                ChassisDriveAmt(-0.32, -0.5, 5.560),
                ChassisDriveAmt(0.0, -0.8, 1.2401),
                ChassisDriveAmt(0.33, -0.5, 4.3729),
                ChassisDriveAmt(0.0, -0.8, 1.392),
                ChassisDriveAmt(0.33, -0.5, 2.7575),
                ChassisDriveAmt(0.0, -0.8, 3.437)
        )

    }
}