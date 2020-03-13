package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj.controller.PIDController
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import kotlin.math.absoluteValue

class AimToTargetPID : CommandBase() {
    companion object {
        private val acceptableError = 0.5

        private val aimPIDController = PIDController(2.0, 4.0, 0.0).also {
            it.setpoint = 0.0
            it.setTolerance(acceptableError)
        }
    }

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private val tx: Double get() = limelightTable.getEntry("tx").getDouble(0.0)
//    private val ty: Double get() = limelightTable.getEntry("ty").getDouble(0.0)
//    private val tz: Double get() = limelightTable.getEntry("tz").getDouble(0.0)

    init {
        addRequirements(Chassis)

        Shuffleboard.getTab("Chassis").add(this)
    }

    override fun initialize() {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID started" }

        if (tx.absoluteValue < acceptableError) {
            this.cancel()
        }
    }

    override fun execute() {
        // Please note tankDrive is inverted on the right side. To drive straight invert the right output.
        val aimAmt = aimPIDController.calculate(tx)

        Chassis.tankDrive(
            aimAmt,
            aimAmt
        )
    }

    override fun end(interrupted: Boolean) {
        KotlinLogging.logger("AimToTargetPID").info { "AimToTargetPID ended" }

        Chassis.tankDrive(0.0, 0.0)
    }

    override fun isFinished(): Boolean {
        return !Cameras.limelightHasTarget || aimPIDController.atSetpoint()
    }

    override fun initSendable(builder: SendableBuilder?) {
        builder!!.setSmartDashboardType("RobotPreferences")

        builder.addDoubleProperty("p", { aimPIDController.p }, { x: Double -> aimPIDController.p = x })
        builder.addDoubleProperty("i", { aimPIDController.i }, { x: Double -> aimPIDController.i = x })
        builder.addDoubleProperty("d", { aimPIDController.d }, { x: Double -> aimPIDController.d = x })

        builder.getEntry("p").setPersistent()
        builder.getEntry("i").setPersistent()
        builder.getEntry("d").setPersistent()
    }

    init {
        Shuffleboard.getTab("Shooter").add(this)
    }
}