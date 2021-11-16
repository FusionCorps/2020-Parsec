package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import mu.KotlinLogging

class CamerasTest : CommandBase() {
    init {
        addRequirements(Cameras)
    }

    override fun execute() {
//         KotlinLogging.logger("Camera Test").info { Cameras.limelightTable.getEntry("tx").getDouble(0.0) }
    }
}