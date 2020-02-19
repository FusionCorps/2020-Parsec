package frc.robot.commands.climb

import com.ctre.phoenix.motorcontrol.TalonFXControlMode
import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.Constants
import frc.robot.subsystems.Climb

class ToggleClaw(climb: Climb) : InstantCommand() {

    private val mClimb = climb

    init {
        addRequirements(Climb)
    }

    override fun initialize() {
        if (mClimb.getClaw() == 0.0) {
            mClimb.setClaw(TalonFXControlMode.Position, Constants.Climb.CLAW_TICKS)
        } else {
            mClimb.setClaw(TalonFXControlMode.Position, 0.0)
        }
    }
}
