package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
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

        addCommands(ChassisDriveTankAmt(1.0*percentOneFtPerSec*radPerSec,
                4.0*percentOneFtPerSec*radPerSec,
                ((90)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        2.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(-1.5*percentOneFtPerSec*radPerSec,
                        1.5*percentOneFtPerSec*radPerSec,
                        ((17.59)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(-percentRunningAt, -percentRunningAt,
                        8.66/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(-3.5*percentOneFtPerSec*radPerSec,
                        -0.5*percentOneFtPerSec*radPerSec,
                        ((180-17.59)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(-percentRunningAt, -percentRunningAt,
                        7.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.0,0.0,
                        0.3),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        7.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((90)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        2.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((90)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        7.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.0,0.0,
                        0.3),
                ChassisDriveTankAmt(-percentRunningAt, -percentRunningAt,
                        2.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(-4.0*percentOneFtPerSec*radPerSec,
                        -1.0*percentOneFtPerSec*radPerSec,
                        ((90)/360*2* PI /radPerSec))
        )

    }
}