package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Shooter
import mu.KotlinLogging

class ShooterCompensateUp : InstantCommand() {
    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        KotlinLogging.logger("ShooterCompensateUp").info { "ShooterCompensateUp Started" }
    }
}
