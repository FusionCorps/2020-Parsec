package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.UpdateShuffleboard
import frc.robot.commands.intake.IntakeRunJoystick
import frc.robot.fusion.motion.*
import mu.KotlinLogging

object ShuffleboardPlacer : SubsystemBase() {

    init {
        defaultCommand = UpdateShuffleboard()
    }

}