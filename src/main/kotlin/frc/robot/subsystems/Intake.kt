package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.intake.IntakeJoystickRun
import mu.KotlinLogging

object Intake: SubsystemBase() {
    private val victorSPXIntake = WPI_VictorSPX(Constants.Intake.ID_VICTORSPX).apply {
        setInverted(InvertType.InvertMotorOutput)

        config_kP(0, 0.5)
        config_kI(0, 0.0)
        config_kP(0, 0.0)
    }

    private val logger = KotlinLogging.logger {}

    init {
        defaultCommand = IntakeJoystickRun(this)
    }

    var currentIntakePercent: Double = 0.0
        set(newTargetIntakeVelocity) {
            logger.info { "New targetIntakeVelocity: $newTargetIntakeVelocity" }
            field = newTargetIntakeVelocity
        }

    fun setBelt(controlMode: VictorSPXControlMode = VictorSPXControlMode.PercentOutput, value: Double) {
        victorSPXIntake.set(controlMode, value)
    }
}
