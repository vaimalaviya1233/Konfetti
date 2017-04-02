package nl.dionsegijn.konfetti

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import nl.dionsegijn.konfetti.models.Vector
import java.util.*

/**
 * TODO: Add lifespan based on milliseconds
 */
class Confetti(var location: Vector,
               val color: Int,
               val size: Size,
               val shape: Shape,
               var acceleration: Vector = Vector(0f, 0f),
               var lifespan: Float = 255f,
               var velocity: Vector = Vector()) {

    private val mass = size.mass
    private var width = size.size
    private val paint: Paint = Paint()

    private var rotationSpeed = 1f
    private var rotation = 0f
    private var rotationWidth = width

    init {
        paint.color = color
        rotationSpeed = 3 * Random().nextFloat() + 1
    }

    fun getSize(): Float {
        return width
    }

    fun isDead(): Boolean {
        return lifespan <= 0
    }

    fun applyForce(force: Vector) {
        val f = force.copy()
        f.div(mass)
        acceleration.add(f)
    }

    fun render(canvas: Canvas) {
        update()
        display(canvas)
    }

    fun update() {
        velocity.add(acceleration)
        location.add(velocity)

        if (location.y > 700) {
            if (lifespan < 0 || lifespan == 0f) lifespan = 0f
            else lifespan -= 5f
        }

        rotation += rotationSpeed
        if (rotation >= 360) rotation = 0f

        rotationWidth -= rotationSpeed
        if (rotationWidth < 0) rotationWidth = width
    }

    fun display(canvas: Canvas) {
        val rect: RectF = RectF(
                location.x + (width - rotationWidth), // center of rotation
                location.y,
                location.x + rotationWidth,
                location.y + getSize())

        paint.alpha = if (lifespan < 0) 0 else lifespan.toInt()

        canvas.save()
        canvas.rotate(rotation.toFloat(), rect.centerX(), rect.centerY())
        when (shape) {
            Shape.CIRCLE -> canvas.drawOval(rect, paint)
            Shape.RECT -> canvas.drawRect(rect, paint)
        }
        canvas.restore()
    }

}
