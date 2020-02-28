package frc.robot.fusion.motion

data class FPIDCharacteristics(var f: Double = 0.0, var p: Double = 0.0, var i: Double = 0.0, var d: Double = 0.0) {
    fun toDoubleArray(): DoubleArray {
        return doubleArrayOf(f, p, i, d)
    }
}
