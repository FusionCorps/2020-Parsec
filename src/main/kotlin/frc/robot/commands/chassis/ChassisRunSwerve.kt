package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.SlewRateLimiter
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis

class ChassisRunSwerve: CommandBase() {
    private val speedLimiter = SlewRateLimiter(2.5) // Cap accel and sens
    private val rotationLimiter = SlewRateLimiter(2.5)

    init {
        addRequirements(Chassis)
    }

    override fun execute() {

    }

}