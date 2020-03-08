package frc.robot.commands.shooter

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.VelocityConfig
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Shooter
import kotlin.math.pow
import kotlin.math.sqrt
import mu.KotlinLogging

class ShooterRunToVelocityAssisted : CommandBase() {
    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")
    private val timer = Timer()
    private val distanceConstant = 800 // per inch away

    init {
        addRequirements(Cameras, Shooter)
    }

    override fun initialize() {
        timer.reset()
        timer.start()

        Cameras.limelightPipeline = 1

        KotlinLogging.logger("VelocityAssisted").info { "VelocityAssisted started" }
    }

    override fun execute() {
        if (timer.hasPeriodPassed(1.0)) {
            val camtran = limelightTable.getEntry("camtran").getDoubleArray(arrayOf())

            val transformedDistance = sqrt(camtran[0].pow(2) + camtran[1].pow(2))

            Shooter.control(ControlMode.Velocity, VelocityConfig((transformedDistance * distanceConstant.toDouble()).toInt()))
        }
    }

    override fun end(interrupted: Boolean) {
        Shooter.control(ControlMode.Disabled)
        KotlinLogging.logger("VelocityAssisted").info { "VelocityAssisted ended" }
    }

    override fun isFinished(): Boolean {
        return (Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity - 150 && Shooter.velocity <= Shooter.motionCharacteristics.velocityConfig!!.velocity + 150) || !limelightTable.getEntry("tv").getBoolean(false)
    }
}
