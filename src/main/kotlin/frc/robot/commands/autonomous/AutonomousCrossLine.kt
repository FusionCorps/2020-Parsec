package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt

class AutonomousCrossLine : SequentialCommandGroup() {
    init {
        addCommands(ChassisDriveAmt(0.4, 0.0, 1.0))
    }
}
