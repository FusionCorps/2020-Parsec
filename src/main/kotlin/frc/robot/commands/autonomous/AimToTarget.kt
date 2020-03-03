package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.abs

class AimToTarget(chassis: Chassis) : CommandBase() {
    private val mChassis = chassis

    private val acceptableError = 0.01

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private var tx: Double = 0.0

    init {
        addRequirements(mChassis)
    }

    override fun initialize() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        if (tx > 0.0) { mChassis.tankDrive(0.5, -0.5) } else { mChassis.tankDrive(-0.5, 0.5) }
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
        mChassis.tankDrive(0.0, 0.0)
    }
}
