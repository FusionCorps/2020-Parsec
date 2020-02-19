package frc.robot.commands.shooter

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.subsystems.Shooter
import mu.KotlinLogging

class ShooterRun(shooter: Shooter, targetVelocity: Double = Constants.Shooter.TARGET_VELOCITY) : CommandBase() {
    private val mShooter = shooter
    private val logger = KotlinLogging.logger("ShooterRun")
    val mTargetVelocity = targetVelocity

    init {
        addRequirements(mShooter)
    }

    override fun initialize() {
        mShooter.setShooter(TalonFXControlMode.Velocity, mTargetVelocity)
    }

    override fun isFinished(): Boolean {
        return mShooter.velocity > mTargetVelocity
    }
}
