package frc.robot.commands.cameras

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras

class CamerasAcquireLimelightTarget(pipeline: Int) : CommandBase() {
    val mPipeline = pipeline

    init {
        addRequirements(Cameras)
    }

    override fun initialize() {
        Cameras.limelightDriverMode = false
        Cameras.limelightPipeline = mPipeline
    }

    override fun isFinished(): Boolean {
        return Cameras.limelightHasTarget
    }
}
