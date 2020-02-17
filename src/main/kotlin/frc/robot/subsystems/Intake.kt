package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import mu.KotlinLogging

object Intake : SubsystemBase() {
    private val victorSPXIntake = VictorSPX(Constants.Indexer.ID_TALONFX)

    private val logger = KotlinLogging.logger {}

    var maxIntakePercent: Double = 0.5
        set(newMaxIntakePercent) {
            logger.info { "New intakeSpd: $newMaxIntakePercent" }
            field = newMaxIntakePercent
        }

    var targetIntakeVelocity: Double = 2400.0  // m/s
        set(newTargetIntakeVelocity) {
            logger.info { "New targetIntakeVelocity: $newTargetIntakeVelocity" }
            field = newTargetIntakeVelocity
        }

    fun setBelt(controlMode: VictorSPXControlMode, value: Double) {
        if (controlMode == VictorSPXControlMode.PercentOutput) {
            victorSPXIntake.set(controlMode, value * maxIntakePercent)
        } else if (controlMode == VictorSPXControlMode.Velocity) {
            victorSPXIntake.set(controlMode, value * targetIntakeVelocity)
        }
    }
}
