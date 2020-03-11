package frc.robot.commands.shooter

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.VelocityConfig
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Shooter
import kotlin.math.pow
import kotlin.math.sqrt
import mu.KotlinLogging

class ShooterRunAndShootAssisted : CommandBase() {
    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val acceptableError = 150
    private val distanceProportion = 800 // per inch away

    init {
        addRequirements(Cameras, Shooter)
    }

    override fun initialize() {
        KotlinLogging.logger("VelocityAssisted").info { "${this::class.simpleName!!} started" }
    }

    override fun execute() {
        val camtran = limelightTable.getEntry("camtran").getDoubleArray(arrayOf())
        val transformedDistance = sqrt(camtran[0].pow(2) + camtran[1].pow(2))

        Shooter.control(ControlMode.Velocity, VelocityConfig((transformedDistance * distanceProportion.toDouble()).toInt()))
    }

    override fun end(interrupted: Boolean) {
        Shooter.control(ControlMode.Disabled)

        KotlinLogging.logger("VelocityAssisted").info { "VelocityAssisted ended" }
    }

    override fun isFinished(): Boolean {
        return (
            Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity - acceptableError &&
                Shooter.velocity <= Shooter.motionCharacteristics.velocityConfig!!.velocity + acceptableError
            ) || !Cameras.limelightHasTarget
    }
}
