package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.hopper.HopperManage
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.fusion.motion.FPIDConfig
import frc.robot.fusion.motion.FVictorSPX
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel

object Hopper : SubsystemBase() {
    private val victorSPXBelt = FVictorSPX(MotorID(Constants.Hopper.ID_VICTORSPX, "VictorSPXBelt", MotorModel.VictorSPX)).apply {
        setInverted(InvertType.InvertMotorOutput)

        control(FPIDConfig(), DutyCycleConfig(0.5))
    }

    val motionCharacteristics: MotionCharacteristics
        get() {
            return victorSPXBelt.motionCharacteristics
        }

    init {
        defaultCommand = HopperManage()
    }

    fun control(vararg config: MotionConfig) {
        victorSPXBelt.control(*config)
    }
}
