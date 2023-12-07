/*
 * Copyright (C) 2023 Meowool <https://github.com/meowool/mmkv-ktx/graphs/contributors>
 *
 * This file is part of the MMKV-KTX project <https://github.com/meowool/mmkv-ktx>.
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

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BasePlugin

plugins {
  alias(libs.plugins.detekt)
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.parcelize) apply false
}

detekt {
  buildUponDefaultConfig = true
  parallel = System.getenv("CI") == null
  config.setFrom(layout.projectDirectory.file("detekt.yml"))
}

allprojects {
  group = "com.meowool"
  version = "0.1.0"
  project.configureAndroid()
}

fun Project.configureAndroid() = plugins.withType<BasePlugin> {
  (extensions["android"] as CommonExtension<*, *, *, *, *>).apply {
    compileSdk = 34
    testOptions.unitTests.isIncludeAndroidResources = true
    defaultConfig {
      minSdk = 16
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      if (this is ApplicationDefaultConfig) targetSdk = 34
    }
    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }
  }
}