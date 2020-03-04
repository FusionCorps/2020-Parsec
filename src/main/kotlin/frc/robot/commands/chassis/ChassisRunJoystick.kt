package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisRunJoystick : CommandBase() {
    private val slewRateLimiter = SlewRateLimiter(0.5)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(
            -slewRateLimiter.calculate(Controls.controller.getRawAxis(4)),
            slewRateLimiter.calculate(Controls.controller.getRawAxis(1))
        )
    }
}
