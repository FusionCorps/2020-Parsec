package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.MotionConfig
import frc.robot.subsystems.Shooter

class ShooterRunAt(
    vararg config: MotionConfig
) : CommandBase() {
    val mConfig = config

    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.control(*mConfig)
    }
}
