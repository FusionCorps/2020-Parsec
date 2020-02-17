package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.can.TalonFX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants

object Shooter : SubsystemBase() {
    private val talonFXLeft = TalonFX(Constants.Shooter.ID_TALONFX_LEFT)
    private val talonFXRight = TalonFX(Constants.Shooter.ID_TALONFX_RIGHT)
}
