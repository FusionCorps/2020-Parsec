package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import kotlin.math.max

class AimToTargetPID : CommandBase() {
    private val maxRotationSpd = 0.2

    private fun convertPIDtoOutput(angle: Double): Double {
        return angle / 27.0 * maxRotationSpd
    }

    private val aimPIDController = PIDController(0.2, 0.0, 0.0).also {
        it.setpoint = 0.0
    }
//    private val distancePIDController = PIDController(0.01, 0.01, 0.0)

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

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

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID started" }
    }

    override fun execute() {
        Chassis.tankDrive(convertPIDtoOutput(aimPIDController.calculate(tx)),
                convertPIDtoOutput(-aimPIDController.calculate(tx)))
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID ended" }
        Chassis.tankDrive(0.0, 0.0)
    }
}
