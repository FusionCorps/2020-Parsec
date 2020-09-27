package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.absoluteValue
import mu.KotlinLogging

class AimToTarget : CommandBase() {
    private val acceptableError = 1.0 // Degrees of target

    private val timer = Timer() // Start Timer

    private val kAim = -0.05 // PID Constant start
    private val kDistance = -0.1
    private val minAim = 0.00

    private var steeringAdjust = 0.0

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight") // Limelight Data get

    private val tx: Double
        get() {
            return limelightTable.getEntry("tx").getDouble(0.0)
        }
    private val ty: Double
        get() {
            return limelightTable.getEntry("ty").getDouble(0.0)
        }
    private val headingError: Double
        get() {
            return -tx
        }
    private val distanceError: Double
        get() {
            return -ty
        }

    // Convert Data

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        KotlinLogging.logger("AimToTarget").info { "AimToTarget started" } // Print to Console

        timer.reset()
        timer.start()
    }

    override fun execute() { // Initiate tank drive with autoaim constants
        if (tx > 1.0) {
            steeringAdjust = kAim * headingError - minAim
        } else if (tx < 1.0) {
            steeringAdjust = kAim * headingError + minAim
        }

//        val distanceAdjust = kDistance * distanceError

        val distanceAdjust = 0

        Chassis.tankDrive(-steeringAdjust + distanceAdjust, -(steeringAdjust + distanceAdjust))
    }

    override fun isFinished(): Boolean { // Finish command
        KotlinLogging.logger("AimToTargetPure").info { "AimToTargetPure ended" }

        return (timer.hasPeriodPassed(2.0) || (tx.absoluteValue < acceptableError && ty.absoluteValue < acceptableError))
    }

    override fun end(interrupted: Boolean) { // Reset tank
        Chassis.tankDrive(0.0, 0.0)
    }
}
