package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class AimToTargetPnP(chassis: Chassis): CommandBase() {

    private val mChassis = chassis

    private val kDistance = 0.005
    private val kRotation = 0.005

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private var tx: Double = 0.0
    private var ty: Double = 0.0
    private var x: Double = 0.0
    private var y: Double = 0.0
    private var z: Double = 0.0
    private var yaw: Double = 0.0

    private var distance = 0.0

    private val targetDistance = 15.0 // NOTE - Check units

    private val distanceTolerance = 1.0
    private val angleTolerance = 1.0

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        x = limelightTable.getEntry("x").getDouble(0.0)
        y = limelightTable.getEntry("y").getDouble(0.0)
        z = limelightTable.getEntry("z").getDouble(0.0)
        yaw = limelightTable.getEntry("yaw").getDouble(0.0)

        distance = sqrt(x.pow(2) + y.pow(2))

        val distanceAdjust = (distance - targetDistance)*kDistance
        val steeringAdjust = tx*kRotation

        mChassis.tankDrive(distanceAdjust - steeringAdjust, distanceAdjust + steeringAdjust)
    }

    override fun isFinished(): Boolean {
        return (abs(tx) < angleTolerance) && (abs(distance - targetDistance) < distanceTolerance)
    }

}