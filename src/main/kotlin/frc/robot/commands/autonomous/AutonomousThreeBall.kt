package frc.robot.commands.autonomous

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.chassis.ChassisDriveAmt
import frc.robot.commands.intake.IntakeRunAt
import frc.robot.commands.shooter.ShooterRunToVelocity
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig

class AutonomousThreeBall : SequentialCommandGroup() {
    init {
        addCommands(
            ChassisDriveAmt(0.4, 0.0, 1.0),
            ShooterRunToVelocity().withTimeout(4.0),
            ChassisDriveAmt(0.4, -0.2, 2.0),
            ChassisDriveAmt(0.4, 0.0, 1.0),
            ChassisDriveAmt(0.4, 0.2, 2.0),
            ParallelCommandGroup(
                IntakeRunAt(ControlMode.DutyCycle, DutyCycleConfig(0.4)),
                ChassisDriveAmt(0.4, 0.0, 2.0)
            ),
            ChassisDriveAmt(-0.4, 0.0, 1.0),
            ParallelCommandGroup(AimToTarget(), ShooterRunToVelocity())
        )
    }
}
