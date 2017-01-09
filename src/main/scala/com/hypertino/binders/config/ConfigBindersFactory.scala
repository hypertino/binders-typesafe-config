package com.hypertino.binders.config

import com.typesafe.config.ConfigValue
import com.hypertino.binders.core.Deserializer
import com.hypertino.inflector.naming.{CamelCaseToDashCaseConverter, Converter}

trait ConfigBindersFactory[C <: Converter, D <: Deserializer[C]] {
  def createDeserializer(fieldValue: Option[ConfigValue], fieldName: Option[String]): D
}

class DefaultConfigBindersFactory[C <: Converter] extends ConfigBindersFactory[C, ConfigDeserializer[C]] {
  def createDeserializer(fieldValue: Option[ConfigValue], fieldName: Option[String]) = new ConfigDeserializer[C](fieldValue, fieldName)
}

object ConfigBindersFactory {
  implicit val defaultSerializerFactory = new DefaultConfigBindersFactory[CamelCaseToDashCaseConverter.type]

  def findFactory[C <: Converter, D <: Deserializer[C]]
    ()(implicit factory: ConfigBindersFactory[C, D]): ConfigBindersFactory[C, D] = factory
}