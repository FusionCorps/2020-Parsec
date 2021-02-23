package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.shooter.ShooterRunToVelocity

class AutonomousSad : SequentialCommandGroup() { // This is the good one.
    init {
//        addCommands(ChassisDriveAmt(-0.4, 0.0, 3.0), ShooterRunToVelocity().withTimeout(6.0))
    }
}
