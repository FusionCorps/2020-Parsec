package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Intake

// Stop intake

class IntakeStop : CommandBase() {
    init {
        addRequirements(Intake)
    }

    override fun initialize() {
        Intake.control(ControlMode.Disabled)
    }
}
