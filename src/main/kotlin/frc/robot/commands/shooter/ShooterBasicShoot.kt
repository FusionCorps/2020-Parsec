package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.subsystems.Shooter

class ShooterBasicShoot(shooter: Shooter) : SequentialCommandGroup() {
    private final val mShooter = shooter

    init {
        addCommands(
            ShooterRunAt()
        )
    }
}
