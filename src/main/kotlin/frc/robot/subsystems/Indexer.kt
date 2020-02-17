package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.fusion.motion.FPIDCharacteristics

object Indexer : SubsystemBase() {
    private val talonFXBelt = WPI_TalonFX(Constants.Indexer.ID_TALONFX)

    var characteristics = FPIDCharacteristics(0.0, 0.1, 0.0, 0.0)

    fun setBelt(controlMode: TalonFXControlMode, value: Double) {
        talonFXBelt.set(controlMode, value)
    }
}
