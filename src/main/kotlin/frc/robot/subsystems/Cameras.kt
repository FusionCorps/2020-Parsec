package frc.robot.subsystems

import edu.wpi.cscore.HttpCamera
import edu.wpi.first.cameraserver.CameraServer
import edu.wpi.first.wpilibj2.command.SubsystemBase

object Cameras : SubsystemBase() {
    private val cameraOne = CameraServer.getInstance().startAutomaticCapture(0)
    private val limelight = CameraServer.getInstance().startAutomaticCapture(HttpCamera("limelight", "http://10.66.72.11:5800/video/stream.mjpg"))
//    private val intakeCamera = CameraServer.getInstance().startAutomaticCapture(UsbCamera("intakeCamera", 0)).apply {
//        setResolution(320, 240)
//        setFPS(30)
//    }
//    private val sink1 = CameraServer.getInstance().getVideo(intakeCamera)
//    private val rearCamera = CameraServer.getInstance().startAutomaticCapture(UsbCamera("rearCamera", 1)).apply {
//        setResolution(320, 240)
//        setFPS(30)
//    }
//    private val sink2 = CameraServer.getInstance().getVideo(rearCamera)
//
//    private val outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240)

//    private val switchedCamera = CameraServer.getInstance().addSwitchedCamera(CameraOptions.Limelight.cameraName)
//    private val limelightTable = NetworkTableInstance.getDefault().getTable("limelight")

//    var source: CameraOptions = CameraOptions.Intake
//        set(value) {
//            switchedCamera.source = value.camera
//            field = value
//        }
//        get() {
//            return CameraOptions.valueOf(switchedCamera.source.name)
//        }
//
//    fun targetPresent(camera: CameraOptions): Boolean {
//        when (camera) {
//            CameraOptions.Limelight -> if (limelightTable.getEntry("tv").getBoolean(false)) { return true }
//        }
//        return false
//    }
//
//    init {
//        CameraServer.getInstance().startAutomaticCapture(switchedCamera)
//    }

//    init {
//        CameraServer.getInstance().startAutomaticCapture()
//    }
}
