package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI

class AutonomousSlalom: SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    init {

        val percentOneFtPerSec = 0.1
        val percentRunningAt = 0.1
        val radPerSec = 1.0

        addCommands(
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                        3.5*percentOneFtPerSec*radPerSec,
                        ((53.13)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        3/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((53.13)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        10/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((53.13)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        3/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                        3.5*percentOneFtPerSec*radPerSec,
                        ((286.26)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        3/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((53.13)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        10/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(3.5*percentOneFtPerSec*radPerSec,
                        0.5*percentOneFtPerSec*radPerSec,
                        ((53.13)/360*2* PI /radPerSec)),
                ChassisDriveTankAmt(percentRunningAt, percentRunningAt,
                        3/percentRunningAt*percentOneFtPerSec),
                ChassisDriveTankAmt(0.5*percentOneFtPerSec*radPerSec,
                        3.5*percentOneFtPerSec*radPerSec,
                        ((286.26)/360*2* PI /radPerSec))
        )




    }

}