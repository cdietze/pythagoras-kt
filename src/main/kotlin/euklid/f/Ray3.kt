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

/**
 * A ray consisting of an origin point and a unit direction vector.
 */
data class Ray3(
        /** The ray's point of origin.  */
        override val origin: Vector3 = Vector3(),
        /** The ray's unit direction vector.  */
        override val direction: Vector3 = Vector3()
) : IRay3 {

    /**
     * Copies the parameters of another ray.
     * @return a reference to this ray, for chaining.
     */
    fun set(other: Ray3): Ray3 = set(other.origin, other.direction)

    /**
     * Sets the ray parameters to the values contained in the supplied vectors.
     * @return a reference to this ray, for chaining.
     */
    fun set(origin: Vector3, direction: Vector3): Ray3 {
        this.origin.set(origin)
        this.direction.set(direction)
        return this
    }
}
