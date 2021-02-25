package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.chassis.ChassisDriveTankAmt
import frc.robot.commands.shooter.ShooterRunToVelocity

class AutonomousSad : SequentialCommandGroup() { // This is the good one.
    init {
        addCommands(ChassisDriveTankAmt(0.5, 0.5, 3.0))
    }
}
