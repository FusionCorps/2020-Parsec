package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI

class AutonomousBounce : SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    init {

        val percentOneFtPerSec = 0.1
        val percentRunningAt = 0.1
        val radPerSec = 1.0

        addCommands(
                ChassisDriveAmt(0.0, -0.5, 0.48),
                ChassisDriveAmt(0.22, -0.5, 1.7),
                ChassisDriveAmt(0.0, -0.5, 0.2),
                ChassisDriveAmt(0.0, -0.2, 0.2),
                ChassisDriveAmt(0.0, 0.2, 0.2),
                ChassisDriveAmt(0.0, 0.5, 0.2),
                ChassisDriveAmt(0.20, 0.5, 0.90),
                ChassisDriveAmt(0.0, 0.8, 1.0),
                ChassisDriveAmt(0.40, 0.5, 1.98),
                ChassisDriveAmt(0.0, 0.8, 0.8),
                ChassisDriveAmt(0.0, 0.5, 0.3),
                ChassisDriveAmt(0.0, 0.0, 0.4),
                ChassisDriveAmt(0.0, -0.5, 1.0),
                ChassisDriveAmt(0.0, -0.8, 0.9),
                ChassisDriveAmt(0.41, -0.5, 0.9)


        )

    }
}