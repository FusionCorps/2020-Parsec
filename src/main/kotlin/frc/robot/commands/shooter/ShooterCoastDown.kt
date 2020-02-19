package frc.robot.commands.shooter

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Shooter

class ShooterCoastDown(shooter: Shooter) : InstantCommand() {
    private final val mShooter = shooter

    init {
        addRequirements(mShooter)
    }

    override fun initialize() {
        mShooter.setShooter(TalonFXControlMode.PercentOutput, 0.0)
    }
}
