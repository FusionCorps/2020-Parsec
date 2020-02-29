package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Intake

class IntakeRunAt(
    vararg configuration: Any
) : CommandBase() {
    val mConfiguration = configuration

    init {
        addRequirements(Intake)
    }

    override fun initialize() {
        Intake.control(*mConfiguration)
    }

    override fun end(interrupted: Boolean) {
        Intake.control(ControlMode.Disabled)
    }
}
