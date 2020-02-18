package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Chassis

class ChassisSetDriveSpd(chassis: Chassis, driveSpdNew: Double) : InstantCommand() {
    private val mChassis = chassis
    private val mDriveSpdNew = driveSpdNew

    init {
        addRequirements(mChassis)
    }

    override fun initialize() {
        mChassis.driveSpd = mDriveSpdNew
    }
}
