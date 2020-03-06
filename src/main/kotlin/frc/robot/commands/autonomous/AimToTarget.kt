package frc.robot.commands.autonomous

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import kotlin.math.abs

class AimToTarget(chassis: Chassis) : CommandBase() {
    private val mChassis = chassis

    private val acceptableErrorX = 1.0
    private val acceptableErrorY = 1.0

    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

    private var tx: Double = 0.0
    private val aimConstant = -0.01

    private val greenZone = 15.5 // Zone where we can shoot

    private var ty: Double = 0.0 // Assumes centered cross hairs
    private val distanceConstant = -0.01

    private var steeringAdjust = 0.0
    private var driveAdjust = 0.0
    private val minimumCommand = 0.03
    private val kAimX = 0.002
    private val kAimY = 0.002

    init {
        addRequirements(mChassis)
    }

    override fun initialize() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        ty = limelightTable.getEntry("ty").getDouble(0.0)
    }

    override fun execute() {
        tx = limelightTable.getEntry("tx").getDouble(0.0)
        ty = limelightTable.getEntry("ty").getDouble(0.0)
        if (abs(tx) > 1.0) {
            steeringAdjust = kAimX * tx
        }
        else {
            steeringAdjust = kAimX * tx  + minimumCommand
        }

        driveAdjust = -ty*kAimY

        mChassis.tankDrive(-steeringAdjust + driveAdjust, steeringAdjust + driveAdjust)

    }
    override fun isFinished(): Boolean {
        return (abs(tx) < acceptableErrorX && abs(ty) < acceptableErrorY)
    }

    override fun end(interrupted: Boolean) {
        mChassis.tankDrive(0.0, 0.0)
    }
}