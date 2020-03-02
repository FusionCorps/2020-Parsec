package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisRunJoystick : CommandBase() {
    private val rateLimiter = SlewRateLimiter(3.0)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(-rateLimiter.calculate(Controls.controller.getRawAxis(4)),
                rateLimiter.calculate(Controls.controller.getRawAxis(1)))
    }
}
