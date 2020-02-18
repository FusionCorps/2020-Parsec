package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisJoystickDrive(chassis: Chassis) : CommandBase() {
    private val mChassis = chassis

    init {
        addRequirements(mChassis)
    }

    override fun execute() {
        mChassis.joystickDrive(Controls.controller.getX(GenericHID.Hand.kLeft), Controls.controller.getY(GenericHID.Hand.kLeft))
    }
}
