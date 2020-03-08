package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.shooter.ShooterRunAndShoot

class AutonomousSad : SequentialCommandGroup() {
    init {
        addCommands(ChassisDriveAmt(-0.4, 0.0, 3.0), ShooterRunAndShoot().withTimeout(6.0))
    }
}
