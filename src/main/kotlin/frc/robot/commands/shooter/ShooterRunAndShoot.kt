package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.VelocityConfig
import frc.robot.subsystems.Indexer
import frc.robot.subsystems.Shooter

class ShooterRunAndShoot(
    velocity: VelocityConfig =
        VelocityConfig(Shooter.motionCharacteristics.velocityConfig!!.velocity)
) :
    CommandBase() {
    val mVelocity = velocity
    private val acceptableError = 150

    init {
        addRequirements(Shooter, Indexer)
    }

    override fun initialize() {
        Shooter.control(
            ControlMode.Velocity,
            mVelocity
        )
    }

    override fun execute() {
        if (Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity - acceptableError &&
            Shooter.velocity <= Shooter.motionCharacteristics.velocityConfig!!.velocity + acceptableError
        ) {
            if (Indexer.motionCharacteristics.controlMode != ControlMode.DutyCycle) {
                Indexer.control(ControlMode.DutyCycle)
            }
        }
    }

    override fun end(interrupted: Boolean) {
        Shooter.control(ControlMode.Disabled)
        Indexer.control(ControlMode.Disabled)
    }
}
