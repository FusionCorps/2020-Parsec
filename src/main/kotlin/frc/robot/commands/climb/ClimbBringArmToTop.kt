package frc.robot.commands.climb

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.Constants
import frc.robot.subsystems.Climb

class ClimbBringArmToTop(climb: Climb) : InstantCommand() {
    private val mClimb = climb

    init {
        addRequirements(climb)
    }

    override fun initialize() {
        mClimb.setHighSpeedMotor(TalonFXControlMode.MotionMagic, Constants.Climb.TICKS_TO_FULL)
    }
}
