package frc.robot.commands.intake

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Intake

class IntakeRun(intake: Intake, controlMode: VictorSPXControlMode, value: Double) : CommandBase() {
    private final val mIntake = intake

    val mControlMode = controlMode
    val mValue = value

    init {
        addRequirements(mIntake)
    }

    override fun initialize() {
        mIntake.setBelt(mControlMode, mValue)
    }
}
