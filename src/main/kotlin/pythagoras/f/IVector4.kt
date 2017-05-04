/*
 * Copyright 2017 The Pythagoras.kt Authors
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



package pythagoras.f

/**
 * Provides read-only access to a [Vector4].
 */
interface IVector4 {
    /** Returns the x-component of this vector.  */
    val x: Float

    /** Returns the y-component of this vector.  */
    val y: Float

    /** Returns the z-component of this vector.  */
    val z: Float

    /** Returns the w-component of this vector.  */
    val w: Float

    /**
     * Compares this vector to another with the provided epsilon.
     */
    fun epsilonEquals(other: IVector4, epsilon: Float): Boolean

    /**
     * Negates this vector.

     * @return a new vector containing the result.
     */
    fun negate(): Vector4

    /**
     * Negates this vector, storing the result in the supplied object.

     * @return a reference to the result, for chaining.
     */
    fun negate(result: Vector4): Vector4

    /**
     * Absolute-values this vector.

     * @return a new vector containing the result.
     */
    fun abs(): Vector4

    /**
     * Absolute-values this vector, storing the result in the supplied object.

     * @return a reference to the result, for chaining.
     */
    fun abs(result: Vector4): Vector4

    /**
     * Multiplies this vector by a scalar.

     * @return a new vector containing the result.
     */
    fun mult(v: Float): Vector4

    /**
     * Multiplies this vector by a scalar and places the result in the supplied object.

     * @return a reference to the result, for chaining.
     */
    fun mult(v: Float, result: Vector4): Vector4

    /**
     * Multiplies this vector by a matrix (V * M).

     * @return a new vector containing the result.
     */
    fun mult(matrix: IMatrix4): Vector4

    /**
     * Multiplies this vector by a matrix (V * M) and stores the result in the object provided.

     * @return a reference to the result vector, for chaining.
     */
    fun mult(matrix: IMatrix4, result: Vector4): Vector4
}