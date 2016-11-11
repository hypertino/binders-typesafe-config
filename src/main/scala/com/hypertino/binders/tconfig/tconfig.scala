package com.hypertino.binders

import com.hypertino.binders.tconfig.internal.ConfigMacro
import com.typesafe.config.{Config, ConfigValue}

import scala.language.experimental.macros

package object tconfig {
  implicit class ConfigReader(val config: Config) extends AnyVal{
    def read[O](path: String): O = macro ConfigMacro.read[O]
  }
  implicit class ConfigValueReader(val configValue: ConfigValue) extends AnyVal{
    def read[O]: O = macro ConfigMacro.readValue[O]
  }
}
