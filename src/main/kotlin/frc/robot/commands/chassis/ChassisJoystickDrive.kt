package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisJoystickDrive(chassis: Chassis) : CommandBase() {
    private final val m_chassis = chassis

    init {
        addRequirements(m_chassis)
    }

    override fun execute() {
        m_chassis.joystick_drive(Controls.controller.getX(), Controls.controller.getY())
    }
}
