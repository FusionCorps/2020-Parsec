package frc.robot.commands

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Chassis
import frc.robot.subsystems.Shooter
import frc.robot.subsystems.ShuffleboardPlacer
import mu.KotlinLogging
import java.lang.Math.abs

class UpdateShuffleboard : CommandBase() {


    val shooterVelEntry = Shuffleboard.getTab("Custom Dash").add("Shooter Speed", Shooter.velocity).entry
    val leftSpeedEntry = Shuffleboard.getTab("Custom Dash").add("Left Speed", Chassis.wheelSpeeds.leftMetersPerSecond).entry
    val rightSpeedEntry = Shuffleboard.getTab("Custom Dash").add("Right Speed", Chassis.wheelSpeeds.rightMetersPerSecond).entry
    val avgSpeedEntry = Shuffleboard.getTab("Custom Dash").add("Chassis Speed",
            abs((Chassis.wheelSpeeds.rightMetersPerSecond+Chassis.wheelSpeeds.leftMetersPerSecond)/2)).entry
    val gyroEntry = Shuffleboard.getTab("Custom Dash").add("Gyro Sendable", Chassis.ahrs)
    val accelXEntry = Shuffleboard.getTab("Custom Dash").add("X Acceleration", Chassis.accelX).entry
    val accelYEntry = Shuffleboard.getTab("Custom Dash").add("Y Acceleration", Chassis.accelY).entry

    init {
        addRequirements(ShuffleboardPlacer)

    }

    override fun initialize() {
    }

    override fun execute() {
//        SmartDashboard.putNumber("Gyro Angle", Chassis.heading)

        shooterVelEntry.setNumber(Shooter.velocity)
        leftSpeedEntry.setNumber(Chassis.wheelSpeeds.leftMetersPerSecond)
        rightSpeedEntry.setNumber(Chassis.wheelSpeeds.rightMetersPerSecond)
        avgSpeedEntry.setNumber(abs((Chassis.wheelSpeeds.rightMetersPerSecond+Chassis.wheelSpeeds.leftMetersPerSecond)/2))
        accelXEntry.setNumber(Chassis.accelX)
        accelYEntry.setNumber(Chassis.accelY)

    }
}