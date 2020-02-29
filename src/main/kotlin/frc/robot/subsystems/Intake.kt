package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.intake.IntakeRunJoystick
import frc.robot.fusion.motion.FVictorSPX
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel
import mu.KotlinLogging

object Intake : SubsystemBase() {
    private val logger = KotlinLogging.logger("Intake")

    private val victorSPXIntake = FVictorSPX(MotorID(Constants.Intake.ID_VICTORSPX, "victorSPXIntake", MotorModel.VictorSPX)).apply {
        configFactoryDefault()

        setInverted(InvertType.InvertMotorOutput)
    }

    init {
        defaultCommand = IntakeRunJoystick()

        Shuffleboard.getTab("Intake").add(victorSPXIntake)
    }

    val motionCharacteristics: MotionCharacteristics
        get() {
            return victorSPXIntake.motionCharacteristics
        }

    fun control(vararg config: MotionConfig) {
        victorSPXIntake.control(*config)
    }
}
