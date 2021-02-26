package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import frc.robot.commands.shooter.ShooterRunToVelocity


// 5.25 s. at 60% power for 20 ft.
// 100% power is 6.35 ft/s
// 80% power is 5.08 ft/s

class AutonomousSad : SequentialCommandGroup() { // This is the good one.
    init {
        addCommands(ChassisDriveAmt(0.0, -0.6, 10.0))
    }
}
