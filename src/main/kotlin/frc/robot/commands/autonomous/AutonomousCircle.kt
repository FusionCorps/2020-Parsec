package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import kotlin.math.PI


class AutonomousCircle : SequentialCommandGroup() {

    // This assumes that driving the motors at 10% will make them go at 1 ft/sec. This is wrong
    // Calibrate this in testing. Delete this comment when the program works.
    // The rotational speed is set to 1 rad/s. This can be changed.

    init {


        addCommands(ChassisDriveAmt(-0.39,
                -0.5,
                4.3)
        )

    }
}