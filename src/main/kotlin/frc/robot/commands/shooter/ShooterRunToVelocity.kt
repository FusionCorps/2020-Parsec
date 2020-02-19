package frc.robot.commands.shooter

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.Constants
import frc.robot.subsystems.Shooter

class ShooterRunToVelocity(shooter: Shooter, velocity: Double = Constants.Shooter.TARGET_VELOCITY) : CommandBase() {
    private final val mShooter = shooter
    val mVelocity = velocity

    init {
        addRequirements(mShooter)
    }

    override fun initialize() {
        mShooter.setShooter(TalonFXControlMode.Velocity, mVelocity)
    }

    override fun isFinished(): Boolean {
        return mShooter.velocity >= mVelocity
    }
}
