package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpiutil.math.MathUtil.clamp
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Chassis
import mu.KotlinLogging

class AimToTargetPID : CommandBase() {
    private val timer = Timer()

    private val acceptableError = 0.0 // Set error and speed
    private val maxRotationSpd = 1.0

    private fun pidToOutput(angle: Double): Double { // Convert PID output to rotation angle
        return clamp(angle, -1.0, 1.0)
    }

    private val aimPIDController = PIDController(0.2, 0.0, 0.0).also {
        it.setpoint = 0.0
        it.setTolerance(acceptableError) // set up PID
    }
//    private val distancePIDController = PIDController(0.01, 0.01, 0.0)

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight") // Get Data

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
        } // Get Data and store

    init {
        addRequirements(Chassis)
    }

    override fun initialize() { // Print console
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID started" }

        Cameras.driverMode = false

        timer.reset()
        timer.start() // Start timer
    }

    override fun execute() {
        // Please note tankDrive is inverted on the right side. To drive straight invert the right output.
        Chassis.tankDrive(
            pidToOutput(aimPIDController.calculate(tx) * maxRotationSpd),
            pidToOutput(aimPIDController.calculate(tx) * maxRotationSpd) // Activate TANK MODE
        )
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID ended" } // Print end

        Chassis.tankDrive(0.0, 0.0)

        Cameras.driverMode = true // Reset
    }

    override fun isFinished(): Boolean {
        // Timer makes sure limelight has enough time to switch modes
        return (timer.hasPeriodPassed(1.0) && tz != 1.0) || (aimPIDController.atSetpoint()) // End
    }
}
