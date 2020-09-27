package frc.robot.commands.intake

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.Controls
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Intake

// Run intake from the joystick

class IntakeRunJoystick : CommandBase() {
    private val slewRateLimiter = SlewRateLimiter(5.0)

    init {
        addRequirements(Intake)
    }

    override fun execute() {
        Intake.control(
            ControlMode.DutyCycle,
            DutyCycleConfig(
                slewRateLimiter.calculate(
                    (
                        Controls.controller.getTriggerAxis(GenericHID.Hand.kRight) -
                            Controls.controller.getTriggerAxis(GenericHID.Hand.kLeft)
                        ) *
                        Constants.Intake.TARGET_PERCENT
                )
            )
        )
    }

    override fun end(interrupted: Boolean) {
        Intake.control(ControlMode.Disabled)
    }
}
