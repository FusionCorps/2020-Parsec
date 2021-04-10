package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis
import mu.KotlinLogging

class ChassisRunJoystick : CommandBase() {
    private val speedLimiter = SlewRateLimiter(7.5) // Cap accel and sens
    private val rotationLimiter = SlewRateLimiter(7.5)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(
            -0.7*speedLimiter.calculate(Controls.controller.getRawAxis(4)), // Drive Chassis
            rotationLimiter.calculate(Controls.controller.getRawAxis(1))
        )



    }
}
