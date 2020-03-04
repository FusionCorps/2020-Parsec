package frc.robot.commands.shooter

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.fusion.motion.AssistedMotionConfig
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.fusion.motion.VelocityConfig
import frc.robot.subsystems.Indexer
import frc.robot.subsystems.Shooter

class ShooterRunToVelocity(velocity: Double = Constants.Shooter.TARGET_VELOCITY) : CommandBase() {
    val mVelocity = velocity
//    val timer = Timer()

    init {
        addRequirements(Shooter, Indexer)
    }

    override fun initialize() {
//        timer.reset()
//        timer.start()

        Shooter.control(
            ControlMode.Velocity,
                VelocityConfig(mVelocity.toInt())
//            AssistedMotionConfig(
//                mVelocity.toInt()
//                Shooter.motionCharacteristics.assistedMotionConfig?.acceleration ?: Constants.Shooter.TARGET_ACCELERATION.toInt()
//            )
        )
    }

    override fun execute() {
        if (Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity) {
//            timer.stop()
            Indexer.control(ControlMode.DutyCycle)
        }
    }

//    override fun isFinished(): Boolean {
//        return Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity
//    }

    override fun end(interrupted: Boolean) {
        Indexer.control(ControlMode.Disabled)
    }
}
