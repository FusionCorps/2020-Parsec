package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.shooter.ShooterRunAndShoot
import frc.robot.subsystems.Chassis

class AutonomousAO : SequentialCommandGroup() {
    init {
        addCommands(ChassisDriveAmt(-0.4, 0.0, 3.0), ShooterRunAndShoot().withTimeout(6.0), ChassisDriveAmt(0.0, 0.05, 1.0))
    }
}
