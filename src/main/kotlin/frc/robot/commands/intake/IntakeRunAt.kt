package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.MotionConfig
import frc.robot.subsystems.Intake

// Run intake with a configuration

class IntakeRunAt(
    vararg config: MotionConfig
) : CommandBase() {
    val mConfig = config

    init {
        addRequirements(Intake)
    }

    override fun initialize() {
        Intake.control(*mConfig)
    }

    override fun end(interrupted: Boolean) {
        Intake.control(ControlMode.Disabled)
    }
}
