package frc.robot.commands.chassis

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis

class ChassisDriveTankAmt(lSpd: Double, rSpd: Double, timeout: Double) : CommandBase() {
    val mLSpd = lSpd
    val mRSpd = rSpd

    val mTimeout = timeout
    val timer = Timer()

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        timer.reset()
        timer.start()
    }

    override fun execute() {
//        Chassis.joystickDrive(mXSpd, mZSpd)
        Chassis.tankDrive(mLSpd, -mRSpd)
    }

    override fun isFinished(): Boolean {
        return timer.hasPeriodPassed(mTimeout)
    }

    override fun end(interrupted: Boolean) {
        Chassis.tankDrive(0.0, 0.0)
    }
}