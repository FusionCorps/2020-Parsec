package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.absoluteValue

class AimToTarget(chassis: Chassis) : CommandBase() {
    private val mChassis = chassis

    private val acceptableError = 0.01

    private val kAim = -0.1
    private val kDistance = -0.1
    private val minDistance = 0.05

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private var tx: Double = 0.0
    private var tz: Double = 0.0

    init {
        addRequirements(mChassis)
    }

    override fun execute() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        tz = limelightTable.getEntry("tz").getDouble(0.0)

        var steeringAdjust = 0.0
        val headingError = -tx
        val distanceError = -tz

        if (tx > 1.0) {
            steeringAdjust = kAim * headingError - minDistance
        } else if (tx < 1.0) {
            steeringAdjust = kAim * headingError + minDistance
        }

        val distanceAdjust = kDistance * distanceError

        val left = steeringAdjust + distanceAdjust
        val right = -steeringAdjust + distanceAdjust

        mChassis.tankDrive(left, right)
    }

    override fun isFinished(): Boolean {
        return tx.absoluteValue < acceptableError && tz.absoluteValue < acceptableError
    }
}
