package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI

class AutonomousBarrelRace : SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    init {

        val percentOneFtPerSec = 0.1
        val percentRunningAt = 0.1
        val radPerSec = 1.0

        addCommands(ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                7.5/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                        3.5*percentOneFtPerSec*radPerSec,
                        ((360-7.5)/360*2*PI/radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        8.077747/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                3.5*percentOneFtPerSec*radPerSec,
                    ((360-52.35)/360*2*PI/radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        7.0710678/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                        3.5*percentOneFtPerSec*radPerSec,
                        ((360-135)/360*2*PI/radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        20/percentRunningAt*percentOneFtPerSec)
        )

    }
}