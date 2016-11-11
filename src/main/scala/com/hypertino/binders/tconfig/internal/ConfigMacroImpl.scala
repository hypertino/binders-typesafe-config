package com.hypertino.binders.tconfig.internal

import com.hypertino.binders.util.MacroAdapter

import scala.language.experimental.macros
import scala.language.reflectiveCalls
import MacroAdapter.Context

private [tconfig] trait ConfigMacroImpl extends MacroAdapter[Context]{
  import ctx.universe._
  def read[O: ctx.WeakTypeTag](path: ctx.Expr[String]): ctx.Tree = {
    val t = freshTerm("t")
    val f = freshTerm("f")
    val d = freshTerm("d")
    val block = q"""{
      val $t = ${ctx.prefix.tree}
      val $f = com.hypertino.binders.tconfig.SerializerFactory.findFactory()
      val $d = $f.createDeserializer(Option($t.config.getValue($path)), Option($path))
      $d.unbind[${weakTypeOf[O]}]
    }"""
    //println(block)
    block
  }
  def readValue[O: ctx.WeakTypeTag]: ctx.Tree = {
    val t = freshTerm("t")
    val f = freshTerm("f")
    val d = freshTerm("d")
    val block = q"""{
      val $t = ${ctx.prefix.tree}
      val $f = com.hypertino.binders.tconfig.SerializerFactory.findFactory()
      val $d = $f.createDeserializer(Option($t.configValue), None)
      $d.unbind[${weakTypeOf[O]}]
    }"""
    //println(block)
    block
  }
}
