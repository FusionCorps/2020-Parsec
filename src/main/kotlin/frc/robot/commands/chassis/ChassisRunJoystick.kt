package frc.robot.commands.chassis


import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Controls
import frc.robot.subsystems.Cameras
import frc.robot.subsystems.Chassis
import mu.KotlinLogging
import kotlin.math.abs

class ChassisRunJoystick : CommandBase() {
    private val speedLimiter = SlewRateLimiter(7.5) // Cap accel and sens
    private val rotationLimiter = SlewRateLimiter(7.5)
    private var tx  = 0.0

    init {
        addRequirements(Chassis)
    }

    override fun execute() {

        if (Chassis.aiming) {
            tx = Cameras.limelightTable.getEntry("tx").getDouble(0.0)
            Chassis.joystickDrive(-0.035*Cameras.limelightTable.getEntry("tx").getDouble(0.0) + (-tx/abs(tx))*0.075, 0.0)
            KotlinLogging.logger("Camera Test").info { Cameras.limelightTable.getEntry("tx").getDouble(0.0) }
        } else {
            Chassis.joystickDrive(
                -0.7 * speedLimiter.calculate(Controls.controller.getRawAxis(4)), // Drive Chassis
                rotationLimiter.calculate(Controls.controller.getRawAxis(1))
            )
        }



    }
}
