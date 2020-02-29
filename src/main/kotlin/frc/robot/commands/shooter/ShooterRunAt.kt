package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Shooter

class ShooterRunAt(
    vararg configuration: Any
) : CommandBase() {
    val mConfiguration = configuration

    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.control(*mConfiguration)
    }
}
