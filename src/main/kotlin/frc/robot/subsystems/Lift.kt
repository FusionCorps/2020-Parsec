package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants

object Lift : SubsystemBase() {
    private val talonFXExtend = TalonFX(Constants.Lift.ID_TALONFX_EXTEND)
    private val talonFXRetract = TalonFX(Constants.Lift.ID_TALONFX_RETRACT)
}
