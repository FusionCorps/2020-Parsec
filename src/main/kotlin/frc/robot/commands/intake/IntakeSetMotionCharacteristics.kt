package frc.robot.commands.intake

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Intake

class IntakeSetMotionCharacteristics(intake: Intake, value: Double) : CommandBase() {
    private final val mIntake = intake
    val mValue = value

    init {
        addRequirements(mIntake)
    }

    override fun initialize() {
        mIntake.currentIntakePercent = mValue
    }
}
