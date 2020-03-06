package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.shooter.ShooterRunToVelocity

class AutonomousSad : SequentialCommandGroup() {
    init {
        addCommands(ChassisDriveAmt(-0.4, 3.0), ShooterRunToVelocity().withTimeout(10.0))
    }
}
