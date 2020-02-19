package frc.robot.commands.climb

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.subsystems.Climb

class LetRobotDown(climb: Climb) : InstantCommand() {

    private val mClimb = climb

    init {
        addRequirements(Climb)
    }

    override fun initialize() {
        mClimb.setHighTorqueMotor(TalonFXControlMode.MotionMagic, 0.0)
    }
}
