package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Chassis

class ChassisSetDriveSpd(chassis: Chassis, driveSpdNew: Double) : InstantCommand() {
    private final val m_chassis = chassis
    private val m_driveSpdNew = driveSpdNew

    init {
        addRequirements(m_chassis)
    }

    override fun initialize() {
        m_chassis.driveSpd = m_driveSpdNew
    }
}
