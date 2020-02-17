package frc.robot.commands.intake

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.Controls
import frc.robot.subsystems.Intake

class IntakeJoystickRun(intake: Intake) : CommandBase() {
    private final val mIntake = intake

    init {
        addRequirements(mIntake)
    }

    override fun execute() {
        mIntake.setBelt(value = (
                (Controls.controller.getTriggerAxis(GenericHID.Hand.kLeft) - Controls.controller.getTriggerAxis(GenericHID.Hand.kRight)) *
                Constants.Intake.TARGET_PERCENT)
        )
    }
}
