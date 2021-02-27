package frc.robot.commands.shooter

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.fusion.motion.ControlMode
import frc.robot.subsystems.Indexer
import frc.robot.subsystems.Shooter

// Run shooter to a velocity (and also indexer)

class ShooterRunToVelocity(velocity: Double = Constants.Shooter.TARGET_VELOCITY) : CommandBase() {
    val mVelocity = velocity

    init {
        addRequirements(Shooter, Indexer)
    }

    override fun initialize() {
        Shooter.control(
            ControlMode.DutyCycle
        )
    }

    override fun execute() {
        if (Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity - 150 &&
            Shooter.velocity <= Shooter.motionCharacteristics.velocityConfig!!.velocity + 150
        ) {

            Indexer.control(ControlMode.DutyCycle)

        }
    }

//    override fun isFinished(): Boolean {
//        return Shooter.velocity >= Shooter.motionCharacteristics.velocityConfig!!.velocity
//    }

    override fun end(interrupted: Boolean) {
        Shooter.control(ControlMode.Disabled)
        Indexer.control(ControlMode.Disabled)
    }
}
