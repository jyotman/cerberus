package xyz

import xyz.jyotman.Permission

package object Types {
  type Data = Map[String, Map[String, Map[String, Permission]]]
}