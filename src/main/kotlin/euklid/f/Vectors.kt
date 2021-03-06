/*
 * Copyright 2017 The Euklid Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package euklid.f

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Vector-related utility methods.
 */
object Vectors {
    /** A unit vector in the X+ direction.  */
    val UNIT_X: IVector = Vector(1f, 0f)

    /** A unit vector in the Y+ direction.  */
    val UNIT_Y: IVector = Vector(0f, 1f)

    /** The zero vector.  */
    val ZERO: IVector = Vector(0f, 0f)

    /** A vector containing the minimum floating point value for all components
     * (note: the components are -[Float.MAX_VALUE], not [Float.MIN_VALUE]).  */
    val MIN_VALUE: IVector = Vector(-Float.MAX_VALUE, -Float.MAX_VALUE)

    /** A vector containing the maximum floating point value for all components.  */
    val MAX_VALUE: IVector = Vector(Float.MAX_VALUE, Float.MAX_VALUE)

    /**
     * Creates a new vector from polar coordinates.
     */
    fun fromPolar(magnitude: Float, angle: Float): Vector {
        return Vector(magnitude * cos(angle), magnitude * sin(angle))
    }

    /**
     * Creates a vector from `from` to `to`.
     */
    fun from(from: XY, to: XY): Vector {
        return Vector(to.x - from.x, to.y - from.y)
    }

    /**
     * Returns the magnitude of the specified vector.
     */
    fun length(x: Float, y: Float): Float {
        return sqrt(lengthSq(x, y))
    }

    /**
     * Returns the square of the magnitude of the specified vector.
     */
    fun lengthSq(x: Float, y: Float): Float {
        return x * x + y * y
    }

    /**
     * Returns true if the supplied vector has zero magnitude.
     */
    fun isZero(x: Float, y: Float): Boolean {
        return x == 0f && y == 0f
    }

    /**
     * Returns true if the supplied vector's x and y components are `epsilon` close to zero
     * magnitude.
     */
    fun isEpsilonZero(x: Float, y: Float, epsilon: Float = MathUtil.EPSILON): Boolean {
        return abs(x) <= epsilon && abs(y) <= epsilon
    }

    /**
     * Returns true if the supplied vectors' x and y components are equal to one another within
     * `epsilon`.
     */
    fun epsilonEquals(v1: IVector, v2: IVector, epsilon: Float = MathUtil.EPSILON): Boolean {
        return abs(v1.x - v2.x) <= epsilon && abs(v1.y - v2.y) <= epsilon
    }

    /** Transforms a vector as specified (as a point, accounting for translation), storing the
     * result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    fun transform(x: Float, y: Float, sx: Float, sy: Float, rotation: Float,
                  tx: Float, ty: Float, result: Vector): Vector {
        return transform(x, y, sx, sy, sin(rotation), cos(rotation), tx, ty,
                result)
    }

    /**
     * Transforms a vector as specified, storing the result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    fun transform(x: Float, y: Float, sx: Float, sy: Float, rotation: Float,
                  result: Vector): Vector {
        return transform(x, y, sx, sy, sin(rotation), cos(rotation), result)
    }

    /**
     * Transforms a vector as specified, storing the result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    fun transform(x: Float, y: Float, sx: Float, sy: Float, sina: Float, cosa: Float,
                  result: Vector): Vector {
        return result.set((x * cosa - y * sina) * sx, (x * sina + y * cosa) * sy)
    }

    /** Transforms a vector as specified (as a point, accounting for translation), storing the
     * result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    fun transform(x: Float, y: Float, sx: Float, sy: Float, sina: Float, cosa: Float,
                  tx: Float, ty: Float, result: Vector): Vector {
        return result.set((x * cosa - y * sina) * sx + tx, (x * sina + y * cosa) * sy + ty)
    }

    /**
     * Inverse transforms a vector as specified, storing the result in the vector provided.
     * @return a reference to the result vector, for chaining.
     */
    fun inverseTransform(x: Float, y: Float, sx: Float, sy: Float, rotation: Float,
                         result: Vector): Vector {
        val sinnega = sin(-rotation)
        val cosnega = cos(-rotation)
        val nx = x * cosnega - y * sinnega // unrotate
        val ny = x * sinnega + y * cosnega
        return result.set(nx / sx, ny / sy) // unscale
    }

    /**
     * Returns a string describing the supplied vector, of the form `+x+y`,
     * `+x-y`, `-x-y`, etc.
     */
    fun vectorToString(x: Float, y: Float): String {
        return MathUtil.toString(x) + MathUtil.toString(y)
    }
}
/**
 * Returns true if the supplied vector's x and y components are [MathUtil.EPSILON] close
 * to zero magnitude.
 */
/**
 * Returns true if the supplied vectors' x and y components are equal to one another within
 * [MathUtil.EPSILON].
 */
