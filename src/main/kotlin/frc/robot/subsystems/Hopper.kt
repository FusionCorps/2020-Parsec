package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.can.VictorSPX
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants

object Hopper : SubsystemBase() {
    private val victorSPXBelt = VictorSPX(Constants.Hopper.ID_VICTORSPX)
}
