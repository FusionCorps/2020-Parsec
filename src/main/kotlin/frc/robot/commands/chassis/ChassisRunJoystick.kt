package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Chassis
import mu.KotlinLogging

class ChassisRunJoystick : CommandBase() {
    private val speedLimiter = SlewRateLimiter(2.5) // Cap accel and sens
    private val rotationLimiter = SlewRateLimiter(2.5)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {
        Chassis.joystickDrive(
            -0.8*speedLimiter.calculate(Controls.controller.getRawAxis(4)), // Drive Chassis
            0.5*rotationLimiter.calculate(Controls.controller.getRawAxis(1))
        )

        KotlinLogging.logger("Drive Forward").info {-speedLimiter.calculate(Controls.controller.getRawAxis(4))}
        KotlinLogging.logger("Drive Rotation").info {rotationLimiter.calculate(Controls.controller.getRawAxis(1))}

    }
}
