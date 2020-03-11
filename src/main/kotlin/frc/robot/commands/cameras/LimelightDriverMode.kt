package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import mu.KotlinLogging

class LimelightDriverMode : CommandBase() {
    init {
        addRequirements(Cameras)
    }

    override fun initialize() {
        Cameras.limelightPipeline = 0
        Cameras.limelightDriverMode = true

        KotlinLogging.logger("CamerasDriverMode").info { "Switching limelight to driver mode" }
    }

    override fun isFinished(): Boolean {
        return !Cameras.limelightHasTarget
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("CamerasDriverMode").info { "Switching limelight to driver mode" }
    }
}
