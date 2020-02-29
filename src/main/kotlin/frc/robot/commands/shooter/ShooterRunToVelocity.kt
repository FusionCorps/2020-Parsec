package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.fusion.motion.AssistedMotionConfig
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Shooter

class ShooterRunToVelocity(velocity: Double = Constants.Shooter.TARGET_VELOCITY) : CommandBase() {
    val mVelocity = velocity

    init {
        addRequirements(Shooter)
    }

    override fun initialize() {
        Shooter.control(
            ControlMode.Velocity,
            AssistedMotionConfig(
                mVelocity.toInt(),
                Shooter.motionCharacteristics.assistedMotionConfig?.acceleration ?: Constants.Shooter.TARGET_ACCELERATION.toInt()
            )
        )
    }

    override fun isFinished(): Boolean {
        return Shooter.velocity >= Shooter.motionCharacteristics.assistedMotionConfig!!.velocity
    }
}
