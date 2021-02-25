package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI


class AutonomousCircle : SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    init {

        val percentOneFtPerSec = 0.2
        val percentRunningAt = 0.5
        val radPerSec = 0.6

        addCommands(ChassisDriveTankAmt(0.02*percentOneFtPerSec*radPerSec,
                6.8*percentOneFtPerSec*radPerSec,
                ((90)/360*2*PI/radPerSec))
        )

    }
}