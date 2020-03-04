package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisRunJoystick : CommandBase() {
    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(
            -Controls.controller.getRawAxis(4),
            Controls.controller.getRawAxis(1)
        )
    }
}
