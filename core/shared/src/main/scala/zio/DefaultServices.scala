/*
 * Copyright 2017-2022 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zio

import zio.internal.stacktracer.Tracer
import zio.stacktracer.TracingImplicits.disableAutoTrace

object DefaultServices {

  /**
   * The default ZIO services.
   */
  val live: ZEnvironment[Clock with Console with System with Random] =
    ZEnvironment[Clock, Console, System, Random](
      Clock.ClockLive,
      Console.ConsoleLive,
      System.SystemLive,
      Random.RandomLive
    )(Clock.tag, Console.tag, System.tag, Random.tag)

  private[zio] val currentServices: FiberRef.WithPatch[ZEnvironment[
    Clock with Console with System with Random
  ], ZEnvironment.Patch[Clock with Console with System with Random, Clock with Console with System with Random]] =
    Unsafe.unsafeCompat(implicit u => FiberRef.unsafe.makeEnvironment(live))
}