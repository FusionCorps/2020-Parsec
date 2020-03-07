package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Chassis
import mu.KotlinLogging

class AimToTargetPID : CommandBase() {
    private val timer = Timer()

    private val acceptableError = 1.0
    private val maxRotationSpd = 0.2

    private fun pidToOutput(angle: Double): Double {
        return angle / 27.0
    }

    private val aimPIDController = PIDController(0.2, 0.0, 0.0).also {
        it.setpoint = 0.0
        it.setTolerance(acceptableError)
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
    private val tz: Double
        get() {
            return limelightTable.getEntry("tz").getDouble(0.0)
        }

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID started" }

        Cameras.driverMode = false

        timer.reset()
        timer.start()
    }

    override fun execute() {
        // Please note tankDrive is inverted on the right side. To drive straight invert the right output.
        Chassis.tankDrive(
            pidToOutput(aimPIDController.calculate(tx)) * maxRotationSpd,
            pidToOutput(aimPIDController.calculate(tx) * maxRotationSpd)
        )
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID ended" }

        Chassis.tankDrive(0.0, 0.0)

        Cameras.driverMode = true
    }

    override fun isFinished(): Boolean {
        // Timer makes sure limelight has enough time to switch modes
        return (timer.hasPeriodPassed(1.0) && tz != 1.0) || (aimPIDController.atSetpoint())
    }
}
