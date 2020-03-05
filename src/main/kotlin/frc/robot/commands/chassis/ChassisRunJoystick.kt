package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis

class ChassisRunJoystick : CommandBase() {
    private val speedLimiter = SlewRateLimiter(2.5)
    private val rotationLimiter = SlewRateLimiter(2.5)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(
            -speedLimiter.calculate(Controls.controller.getRawAxis(4)),
            rotationLimiter.calculate(Controls.controller.getRawAxis(1))
        )
    }
}
