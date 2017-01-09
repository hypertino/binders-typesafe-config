package com.hypertino.binders.config

import com.hypertino.binders.config.internal.ConfigMacro
import com.typesafe.config.{Config, ConfigValue}

import scala.language.experimental.macros

object ConfigBinders {
  implicit class ConfigReader(val config: Config) extends AnyVal{
    def read[O](path: String): O = macro ConfigMacro.read[O]
  }
  implicit class ConfigValueReader(val configValue: ConfigValue) extends AnyVal{
    def read[O]: O = macro ConfigMacro.readValue[O]
  }
}
