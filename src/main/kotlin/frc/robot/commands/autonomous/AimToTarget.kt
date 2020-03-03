package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.abs
import kotlin.math.absoluteValue

class AimToTarget(chassis: Chassis) : CommandBase() {
    private val mChassis = chassis

    private val acceptableError = 0.01

    private val kAim = -0.1

    private val minDistance = 0.05

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private var tx: Double = 0.0

    private var dReq: Double = 0.0

    private val chassisWidth = 1.0 // meters

    private val wheelDiameter = 0.1 // meters

    init {
        addRequirements(mChassis)
    }

    override fun initialize() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        if (tx > 0.0) {mChassis.tankDrive(0.5, -0.5)}
        else {mChassis.tankDrive(-0.5, 0.5)}
    }

    override fun execute() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
    }
    override fun isFinished(): Boolean {
        if (abs(tx) < acceptableError) {
            return true
        }
        return false
    }

    override fun end(interrupted: Boolean) {
        mChassis.tankDrive(0.0 ,0.0)
    }
}
