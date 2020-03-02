package frc.robot.commands.intake

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.Controls
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Intake

class IntakeRunJoystick : CommandBase() {
    private val slewRateLimiter = SlewRateLimiter(3.0)

    init {
        addRequirements(Intake)
    }

    override fun execute() {
        Intake.control(
            DutyCycleConfig(
                slewRateLimiter.calculate((Controls.controller.getTriggerAxis(GenericHID.Hand.kLeft) -
                        Controls.controller.getTriggerAxis(GenericHID.Hand.kRight)) *
                    Constants.Intake.TARGET_PERCENT)
            )
        )
    }

    override fun end(interrupted: Boolean) {
        Intake.control(ControlMode.Disabled)
    }
}
