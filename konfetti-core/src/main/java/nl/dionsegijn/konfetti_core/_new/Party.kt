package nl.dionsegijn.konfetti_core._new

import android.graphics.Color
import nl.dionsegijn.konfetti_core._new.NewEmitter.EmitterConfig
import nl.dionsegijn.konfetti_core._new.PartyEmitter.PartyEmitter
import nl.dionsegijn.konfetti_core.models.Shape
import nl.dionsegijn.konfetti_core.models.Size

data class Party(
    val angle: Int = 0,
    val spread: Int = 20,
    val startVelocity: Int = 20, // Add min and max velocity
    val size: List<Size> = listOf(Size.SMALL, Size.MEDIUM, Size(10)),
    val colors: List<Int> = listOf(Color.RED),
    val shapes: List<Shape> = listOf(Shape.Square, Shape.Circle),
    val timeToLive: Long = 2000, // milliseconds
    val fadeOutEnabled: Boolean = true,
    val position: Position = Position(100f, 100f),
    val delay: Int = 0,
    val speedDensityIndependent: Boolean = true,
    val accelerationEnabled: Boolean = true,
    val maxAcceleration: Float = -1f, // TODO divide maxAccelation by 10 in Confetti
    val rotation: Rotation = Rotation(),
    val emitter: EmitterConfig
)


// TODO Support relative position
data class Position(val x: Float, val y: Float)

data class Rotation(
    val enabled: Boolean = true,
    val baseRotationMultiplier: Float = 1f,
    val rotationVariance: Float = 0.2f
) {
    companion object {
        fun disabled() = Rotation(enabled = false)
    }
}

fun newKonfetti() {
    val party = Party(
        startVelocity = 5,
        angle = 90, // TOP
        spread = 70, // spread will look like: \/
        emitter = PartyEmitter.burst(20),
        rotation = Rotation.disabled()
    )

    val partySystem = PartySystem(party)

    while (true) {
        partySystem.render(16f)
    }
}
