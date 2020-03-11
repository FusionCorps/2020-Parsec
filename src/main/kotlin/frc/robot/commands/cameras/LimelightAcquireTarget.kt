package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import mu.KotlinLogging

class LimelightAcquireTarget(pipeline: Int) : CommandBase() {
    val mPipeline = pipeline

    init {
        addRequirements(Cameras)
    }

    override fun initialize() {
        Cameras.limelightDriverMode = false
        Cameras.limelightPipeline = mPipeline

        KotlinLogging.logger("CamerasAcquireLimelightTarget").info { "Limelight acquiring target on pipeline $mPipeline..." }
    }

    override fun isFinished(): Boolean {
        return Cameras.limelightHasTarget
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("CamerasAcquireLimelightTarget").info { "Limelight has acquired a target" }
    }
}
